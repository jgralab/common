package de.uni_koblenz.ist.utilities.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public abstract class SwingApplication extends JFrame {
	public static boolean RUNS_ON_WINDOWS = System.getProperty("os.name")
			.toLowerCase().startsWith("windows");
	public static boolean RUNS_ON_LINUX = System.getProperty("os.name")
			.toLowerCase().startsWith("linux");
	public static boolean RUNS_ON_MAC_OS_X = System.getProperty("os.name")
			.toLowerCase().startsWith("mac os x");

	protected Action fileNewAction;
	protected Action fileOpenAction;
	protected Action fileSaveAction;
	protected Action fileSaveAsAction;
	protected Action fileCloseAction;
	protected Action filePrintAction;
	protected Action fileExitAction;

	protected Action helpAboutAction;

	private boolean modified;
	private JMenuBar menuBar;
	private JPanel contentPanel;
	private StatusBar statusBar;

	public SwingApplication(String title) {
		super(title);
	}

	public void initializeApplication() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager
			// .getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (RUNS_ON_MAC_OS_X) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}

		final int menuEventMask = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		createActions(menuEventMask);
		setJMenuBar(createMenuBar());
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		Component content = createContent();
		contentPanel.add(content, BorderLayout.CENTER);
		statusBar = createStatusBar();
		contentPanel.add(statusBar, BorderLayout.SOUTH);

		getContentPane().add(contentPanel);
		updateActions();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fileExit();
			}
		});
		pack();
	}

	protected void createActions(final int menuEventMask) {
		fileNewAction = new AbstractAction("New ...") {
			{
				putValue(AbstractAction.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(KeyEvent.VK_N, menuEventMask));
			}

			public void actionPerformed(ActionEvent e) {
				fileNew();
			}
		};

		fileOpenAction = new AbstractAction("Open ...") {
			{
				putValue(AbstractAction.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(KeyEvent.VK_O, menuEventMask));
			}

			public void actionPerformed(ActionEvent e) {
				fileOpen();
			}
		};

		fileSaveAction = new AbstractAction("Save") {
			{
				putValue(AbstractAction.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(KeyEvent.VK_S, menuEventMask));
			}

			public void actionPerformed(ActionEvent e) {
				fileSave();
			}

		};

		fileSaveAsAction = new AbstractAction("Save as ...") {
			{
				putValue(AbstractAction.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(KeyEvent.VK_S,
								KeyEvent.SHIFT_DOWN_MASK | menuEventMask));
			}

			public void actionPerformed(ActionEvent e) {
				fileSaveAs();
			}
		};

		fileCloseAction = new AbstractAction("Close ...") {
			{
				putValue(AbstractAction.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(KeyEvent.VK_W, menuEventMask));
			}

			public void actionPerformed(ActionEvent e) {
				fileClose();
			}
		};

		filePrintAction = new AbstractAction("Print ...") {
			{
				putValue(AbstractAction.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(KeyEvent.VK_P, menuEventMask));
			}

			public void actionPerformed(ActionEvent e) {
				filePrint();
			}
		};

		fileExitAction = new AbstractAction("Exit") {
			{
				putValue(AbstractAction.ACCELERATOR_KEY,
						KeyStroke.getKeyStroke(KeyEvent.VK_Q, menuEventMask));
			}

			public void actionPerformed(ActionEvent e) {
				fileExit();
			}
		};

		helpAboutAction = new AbstractAction("About " + getTitle() + " ...") {
			public void actionPerformed(ActionEvent e) {
				helpAbout();
			}
		};
	}

	protected JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(fileNewAction);
		fileMenu.add(fileOpenAction);
		fileMenu.addSeparator();
		fileMenu.add(fileCloseAction);
		fileMenu.add(fileSaveAction);
		fileMenu.add(fileSaveAsAction);
		fileMenu.addSeparator();
		fileMenu.add(filePrintAction);
		if (RUNS_ON_MAC_OS_X) {
			try {
				OSXAdapter.setQuitHandler(this, SwingApplication.class
						.getDeclaredMethod("fileExit", new Class<?>[] {}));
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			fileMenu.addSeparator();
			fileMenu.add(fileExitAction);
		}
		menuBar.add(fileMenu);

		JMenu helpMenu = new JMenu("Help");
		// System.out.println(helpMenu);
		if (RUNS_ON_MAC_OS_X) {
			try {
				OSXAdapter.setAboutHandler(this, SwingApplication.class
						.getDeclaredMethod("helpAbout", new Class<?>[] {}));
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			helpMenu.add(helpAboutAction);
		}
		menuBar.add(helpMenu);
		// helpMenu.putClientProperty(JComponent., value)
		return menuBar;
	}

	protected ImageIcon getApplicationIcon() {
		return null;
	}

	protected StatusBar createStatusBar() {
		return new StatusBar();
	}

	protected void updateActions() {

	}

	protected void fileNew() {

	}

	protected void fileOpen() {

	}

	protected void fileSave() {

	}

	protected void fileSaveAs() {

	}

	protected void fileClose() {

	}

	protected void filePrint() {

	}

	protected boolean fileExit() {
		if (confirmClose()) {
			System.exit(0);
		}
		return false;
	}

	protected void helpAbout() {
	}

	protected abstract Component createContent();

	protected boolean confirmClose() {
		if (!isModified()) {
			return true;
		}

		return false;
	}

	public abstract String getVersion();

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		if (this.modified == modified) {
			return;
		}
		if (RUNS_ON_MAC_OS_X) {
			getRootPane()
					.putClientProperty("Window.documentModified", modified);
		}
		this.modified = modified;
	}

	public static void invokeAndWait(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public static void invokeLater(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}
}
