package de.uni_koblenz.ist.utilities.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private MouseMotionListener mouseMotionListener;
	private RenderingHints antialiasOn;
	private Point2D.Double origin;
	private Point2D.Double offset;
	private Point2D.Double mouseDownOrigin;
	private BoundedRangeModel zoomLevelModel;
	private Rectangle2D boundingBox;

	public BoundedRangeModel getZoomLevelModel() {
		return zoomLevelModel;
	}

	private double scale;
	private double pixelPerUnit;

	private Point mouseDownPoint;
	private boolean mouseIsDown;
	private Point mouseUpPoint;

	private long lastRepaint;

	public static final int ZOOM_MAX = 160;
	public static final int ZOOM_INIT = 80;
	public static final int ZOOM_MIN = 0;
	public static final int CONTINUOUS_DRAG_DELAY = 20;

	// 1: y grows from top to bottom
	// -1 = y grows from bottom to top
	private int my;

	// true: origin is in center of window
	// false: origin is top left or bottom left
	private boolean originInCenter;

	public DrawingPanel(boolean originInCenter, boolean positiveYAxis) {
		this.originInCenter = originInCenter;
		my = positiveYAxis ? 1 : -1;
		pixelPerUnit = 1.0;

		// antialiasOff = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_OFF);

		antialiasOn = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		antialiasOn.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		origin = new Point2D.Double();
		offset = new Point2D.Double();
		mouseDownOrigin = new Point2D.Double();

		zoomLevelModel = new DefaultBoundedRangeModel(ZOOM_INIT, 0, ZOOM_MIN,
				ZOOM_MAX);
		scale = 1.0;
		zoomLevelModel.setValue(ZOOM_INIT);
		zoomLevelModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				scale = zoomToScale(zoomLevelModel.getValue());
				if (isVisible()) {
					repaint();
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			private Cursor savedCursor;

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseDownPoint = e.getPoint();
					mouseDownOrigin.setLocation(origin);
					mouseIsDown = true;
					savedCursor = getCursor();
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseUpPoint = e.getPoint();
					mouseIsDown = false;
					double dx = (mouseUpPoint.x - mouseDownPoint.x)
							/ pixelPerUnit / scale;
					double dy = (mouseUpPoint.y - mouseDownPoint.y)
							/ pixelPerUnit / scale;
					origin.setLocation(mouseDownOrigin.x + dx,
							mouseDownOrigin.y + my * dy);
					setCursor(savedCursor);
					repaint();
				}
			}
		});

		mouseMotionListener = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (mouseIsDown) {
					long now = System.currentTimeMillis();
					if (now - lastRepaint >= CONTINUOUS_DRAG_DELAY) {
						Point p = e.getPoint();
						double dx = (p.x - mouseDownPoint.x) / pixelPerUnit
								/ scale;
						double dy = (p.y - mouseDownPoint.y) / pixelPerUnit
								/ scale;
						origin.setLocation(mouseDownOrigin.x + dx,
								mouseDownOrigin.y + my * dy);
						repaint();
					}
				}
			}

		};
		addMouseMotionListener(mouseMotionListener);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		Dimension size = getSize();
		Insets i = getInsets();
		g2.setColor(getBackground());
		size.width -= i.left + i.right;
		size.height -= i.top + i.bottom;
		g2.translate(i.left, i.top);
		g2.setClip(0, 0, size.width, size.height);
		g2.fillRect(0, 0, size.width, size.height);
		if (originInCenter) {
			g2.translate(size.width / 2, size.height / 2);
		} else {
			if (my == 1) {
				g2.translate(0, 0);
			} else {
				g2.translate(0, size.height);
			}
		}

		g2.scale(scale * pixelPerUnit, my * scale * pixelPerUnit);
		g2.translate(origin.x + offset.x, origin.y + offset.y);
		g2.setRenderingHints(antialiasOn);

		paintContent(g2);
		lastRepaint = System.currentTimeMillis();
	}

	protected void paintContent(Graphics2D g2) {
	}

	public double getPixelPerUnit() {
		return pixelPerUnit;
	}

	public void setPixelPerUnit(double pixelPerUnit) {
		this.pixelPerUnit = pixelPerUnit;
	}

	public Point2D.Double getOffset() {
		return offset;
	}

	public void setOffset(Point2D.Double offset) {
		this.offset = offset;
	}

	public Rectangle2D getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle2D bbx) {
		this.boundingBox = bbx;
	}

	public void zoomToFit() {
		if (boundingBox == null) {
			return;
		}
		origin.setLocation(0, 0);
		double sc = Math.min(getWidth() / boundingBox.getWidth(), getHeight()
				/ boundingBox.getHeight());
		zoomLevelModel.setValue(scaleToZoom(sc));
	}

	private double zoomToScale(int z) {
		return Math.pow(10.0, z / 40.0) / 100.0;
	}

	private int scaleToZoom(double sc) {
		return Math.max(
				ZOOM_MIN,
				Math.min(ZOOM_MAX,
						(int) (Math.round(Math.log10(sc * 100.0) * 40.0))));
	}

	public void center() {
		origin.setLocation(0, 0);
		zoomLevelModel.setValue(ZOOM_INIT);
		if (isVisible()) {
			repaint();
		}
	}
}
