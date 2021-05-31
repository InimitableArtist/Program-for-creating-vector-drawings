package hr.fer.ooup.lab4;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class GUI extends JFrame {
	
	private DocumentModel model;
	private Canvas canvas;
	private State currentState;
	private Map<String, GraphicalObject> prototypes;
	private List<GraphicalObject> objects;
	
	public GUI(List<GraphicalObject> objects) {
		model = new DocumentModel();
		this.objects = objects;
		currentState = new IdleState();
		prototypes = new HashMap<String, GraphicalObject>();
		initGUI(this.objects);
	}
	private void initGUI(List<GraphicalObject> objects) {
		addComponents(objects);
		addToolbar(objects);
		addListeners(objects);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void addListeners(List<GraphicalObject> objects) {
		model.addDocumentModelListener(new DocumentModelListener() {
			
			@Override
			public void documentChange() {
				canvas.repaint();
				
			}
		});
		
		canvas.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					currentState.onLeaving();
					currentState = new IdleState();
				} else {
					currentState.keyPressed(e.getKeyCode());
				}
			}
			
			
		});
		
		canvas.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mousePressed(MouseEvent e) {
				currentState.mouseDown(e.getPoint(), e.isShiftDown(), e.isControlDown());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				currentState.mouseUp(e.getPoint(), e.isShiftDown(), e.isControlDown());
			}
			
			
		});
		canvas.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				currentState.mouseDragged(e.getPoint());
			}
		});
	
	}

	private void addComponents(List<GraphicalObject> objects) {
		canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);
		
		for (GraphicalObject go : objects) {
			//model.addGraphicalObject(go);
			prototypes.put(go.getShapeID(), go);
		}
		GraphicalObject compPrototype = new CompositeShape();
		prototypes.put(compPrototype.getShapeID(), compPrototype);
		
		
	}
	
	private void addToolbar(List<GraphicalObject> objects) {
		JToolBar tBar = new JToolBar();
		
		for (GraphicalObject go : objects) {
			Action action = new CanvasAction(go);
			action.putValue(Action.NAME, go.getShapeName());
			tBar.add(action);
		}
		selectAction.putValue(Action.NAME, "Selektiraj");
		tBar.add(selectAction);
		
		eraseAction.putValue(Action.NAME, "Brisalo");
		tBar.add(eraseAction);
		
		tBar.setFloatable(false);
		add(tBar, BorderLayout.NORTH);
		
		svgExportAction.putValue(Action.NAME, "SVG Export");
		tBar.add(svgExportAction);
		
	}
	
	private Action eraseAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currentState.onLeaving();
			currentState = new EraserState(model);
		}
		
	};
	
	private Action selectAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentState.onLeaving();
			currentState = new SelectShapeState(model);
		}
	};
	
	private Action svgExportAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser f = new JFileChooser();
			f.setDialogTitle("SVG Export");
			if(f.showSaveDialog(GUI.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			String path = f.getSelectedFile().getPath();
			if(!path.endsWith(".svg")) {
				path += ".svg";
			}
			SVGRendererImpl svgRenderer = new SVGRendererImpl(path);
			List<GraphicalObject> objects = model.list();
			for (GraphicalObject go : objects) {
				go.render(svgRenderer);
			}
			try {
				svgRenderer.close();
				JOptionPane.showMessageDialog(GUI.this, "SVG file successfully generated.", "INFO", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(GUI.this, "While exporting to file " + path + ": " + e1, "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};
	
	private class CanvasAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		
		private GraphicalObject go;
		
		public CanvasAction(GraphicalObject go) {
			this.go = go;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currentState.onLeaving();
			currentState = new AddShapeState(model, go);
		}
	}
	private class Canvas extends JComponent {

		private static final long serialVersionUID = 1L;
		
		public Canvas() {
			setFocusable(true);
			requestFocusInWindow();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			G2DRendererImpl renderer = new G2DRendererImpl((Graphics2D)g);
			List<GraphicalObject> objects = model.list();
			for (GraphicalObject go : objects) {
				go.render(renderer);
				currentState.afterDraw(renderer, go);
			}
			currentState.afterDraw(renderer);
			requestFocusInWindow();
		}
	}
	public static void main(String[] args) {
			
			SwingUtilities.invokeLater(new Runnable() {
	
				@Override
				public void run() {
					List<GraphicalObject> objects = new ArrayList<GraphicalObject>();
					objects.add(new LineSegment());
					objects.add(new Oval());
					new GUI(objects).setVisible(true);
				}
				
			});
			
	
		}

}
