package app;

import objectdata.Scene;
import objectdata.Solid;
import objectdata.objects.*;
import rasterdata.ColorRaster;
import rasterdata.TextureRaster;
import rasterdata.ZBuffer;
import render.Renderer3D;
import shader.FragmentShader;
import shader.TextureColor;
import view.Panel;
import transforms.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    private final Panel panel;
    Renderer3D renderer3D;
    private ZBuffer zBuffer;
    private Point mousePos = new Point();
    Scene scene;
    private List<Solid> solids = new ArrayList<>();
    private Solid activeSolidObject;
    private Mat4 viewMatrix = new Mat4Identity();
    private Mat4 projection = new Mat4Identity();
    private Camera camera;
    private Cube cube;
    private Plumbob plumbob;
    private TriangularPrism prism;
    private int activeSolid = 1;
    private int activeAxis = 0;
    private boolean persp = true;
    private boolean wired = false;


    public Controller(Panel panel) {
        this.panel = panel;

        init(panel.getRaster());


        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mousePos = e.getPoint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                double azimut = Math.PI * ((float) (mousePos.x - e.getX()) / panel.getRaster().getWidth());
                double zenit = Math.PI * ((float) (mousePos.y - e.getY()) / panel.getRaster().getHeight());

                // making sure azimuth works
                azimut = Math.max(Math.min(azimut, Math.PI / 2), -Math.PI / 2);

                // making sure zenith works
                zenit = Math.max(Math.min(zenit, Math.PI / 2), -Math.PI / 2);

                camera = camera.addAzimuth(azimut);
                camera = camera.addZenith(zenit);

                redraw();

                mousePos = e.getPoint();

            }
        });

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent key) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4Transl(0, -0.1, 0)));
                    case KeyEvent.VK_RIGHT -> activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4Transl(0, 0.1, 0)));
                    case KeyEvent.VK_UP -> activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4Transl(-0.1, 0, 0)));
                    case KeyEvent.VK_DOWN -> activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4Transl(0.1, 0, 0)));
                    case KeyEvent.VK_SHIFT -> activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4Transl(0, 0, 0.1)));
                    case KeyEvent.VK_CONTROL -> activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4Transl(0, 0, -0.1)));
                    case KeyEvent.VK_W -> camera = camera.up(0.1);
                    case KeyEvent.VK_S -> camera = camera.down(0.1);
                    case KeyEvent.VK_A -> camera = camera.left(0.1);
                    case KeyEvent.VK_D -> camera = camera.right(0.1);
                    case KeyEvent.VK_Q -> camera = camera.forward(0.1);
                    case KeyEvent.VK_E -> camera = camera.backward(0.1);
                    case KeyEvent.VK_P -> persp = !persp;
                    case KeyEvent.VK_X -> wired = !wired;
                    case KeyEvent.VK_R -> {
                        switch (activeAxis) {
                            case 0 -> {
                                activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4RotX(0.01)));
                            }
                            case 1 -> {
                                activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4RotY(0.01)));
                            }
                            case 2 -> {
                                activeSolidObject.setModelMat(activeSolidObject.getModelMat().mul(new Mat4RotZ(0.01)));
                            }
                        }
                    }
                    case KeyEvent.VK_N -> {
                        for (Solid solid : solids){
                            solid.picked = false;
                        }
                        switch (activeSolid) {
                            case 0 -> {
                                activeSolidObject = cube;
                                activeSolid++;
                            }
                            case 1 -> {
                                activeSolidObject = plumbob;
                                activeSolid++;
                            }
                            case 2 -> {
                                activeSolidObject = prism;
                                activeSolid = 0;
                            }
                        }
                    }
                    case KeyEvent.VK_T -> {
                        activeAxis++;
                        if(activeAxis == 3) activeAxis = 0;
                    }
                }

                redraw();
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                viewMatrix = camera.getViewMatrix();
                init(panel.getRaster());
            }
        });

        redraw();
    }

    public void init(ColorRaster raster) {
        raster.clear(0x101010);

        zBuffer = new ZBuffer(raster);
        renderer3D = new Renderer3D();
        cube = new Cube();
        plumbob = new Plumbob();
        prism = new TriangularPrism();

        solids.addAll(List.of(new Axis(), cube, plumbob, prism));
        activeSolidObject = cube;

        scene = new Scene(solids);
        camera = new Camera(new Vec3D(3, 1.5, 4), -Math.PI / 1.15, -Math.PI / 3.7, 1, true);
   }

    private void redraw() {
        panel.clear(0x101010);
        zBuffer.clear(0x101010);

        viewMatrix = camera.getViewMatrix();

        if (persp) {
            projection = new Mat4PerspRH(20, (float) panel.getRaster().getHeight() / panel.getRaster().getWidth(), 0.1, 50);
        } else {
            projection = new Mat4OrthoRH(6, 4, 0.1, 50);

        }

        TextureRaster texture = new TextureRaster("res/trippy.jpg");
        FragmentShader logo = new TextureColor(texture);

        renderer3D.renderScene(scene, viewMatrix, projection, zBuffer, logo, wired);

        panel.repaint();
    }
}
