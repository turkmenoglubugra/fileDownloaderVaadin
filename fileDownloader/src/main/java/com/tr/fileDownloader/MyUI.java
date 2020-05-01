package com.tr.fileDownloader;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	private FileDownloader fileDownloader;
	private String path;
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		try {
		final VerticalLayout layout = new VerticalLayout();

		Button downloadButton = new Button("Create File");
		Button downloadButton2 = new Button("Load Downloader");
		Button downloadButton3 = new Button("Download File");



		final TextField name = new TextField();
		name.setCaption("Type your name here:");
		downloadButton.addClickListener(e -> {
	        FileWriter writer;
			try {
				path = String.valueOf(ThreadLocalRandom.current().nextInt());
				writer = new FileWriter("D:\\"+path+".zpl");
				writer.write(path);
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		downloadButton2.addClickListener(e -> {
			if (fileDownloader != null && downloadButton3.getExtensions().contains(fileDownloader)) {
				downloadButton3.removeExtension(fileDownloader);
			} 
			StreamResource myResource = createResource();
			fileDownloader = new FileDownloader(myResource);
			fileDownloader.extend(downloadButton3);
		});
		layout.addComponents(name, downloadButton,downloadButton2,downloadButton3);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private StreamResource createResource() {
		return new StreamResource(new StreamSource() {
			@Override
			public InputStream getStream() {
				String text = "My adsasdimage";	

				BufferedImage bi = new BufferedImage(100, 30, BufferedImage.TYPE_3BYTE_BGR);
				bi.getGraphics().drawChars(text.toCharArray(), 0, text.length(), 10, 20);

				try {
					return new ByteArrayInputStream(Files.readAllBytes(new File("D:\\"+path+".zpl").toPath()));
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}

			}
		}, path+".zpl");
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
