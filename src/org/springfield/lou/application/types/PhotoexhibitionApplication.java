/* 
* PhotoexhibitionApplication.java
* 
* Copyright (c) 2015 Noterik B.V.
* 
* This file is part of smt_beerapp, related to the Noterik Springfield project.
*
* smt_beerapp is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* smt_beerapp is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with smt_beerapp.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.springfield.lou.application.types;

import java.util.ArrayList;
import java.util.List;

import org.springfield.fs.Fs;
import org.springfield.fs.FsNode;
import org.springfield.lou.application.Html5Application;
import org.springfield.lou.screen.Capabilities;
import org.springfield.lou.screen.Screen;

/**
 * PhotoexhibitionApplication.java
 *
 * @author Pieter van Leeuwen
 * @copyright Copyright: Noterik B.V. 2015
 * @package org.springfield.lou.application.types
 * 
 */
public class PhotoexhibitionApplication extends Html5Application {

 	private static final String themeBaseUri = "/domain/espace/user/photoexhibition/collection/";
	
	public PhotoexhibitionApplication(String id) {
		super(id);
	}
	
	public void init(Screen s) {
 		
 	}
	
	public void onNewScreen(Screen s) {
		String role = s.getParameter("role");
    	role = role == null ? "" : role;
		
		Capabilities caps = s.getCapabilities();
		int device = caps.getDeviceMode();
		
		if (device != caps.MODE_GENERIC || role.equals("mobile")) {
			//second screens
			loadStyleSheet(s,"mobile");
			s.setRole("mainscreen");
			this.loadContent(s, "secondscreen");
			
			loadThemes();
		} else {
			//main screens
			loadStyleSheet(s,"generic");
			s.setRole("mainscreen");
			this.loadContent(s, "mainscreen");
		}
	}
	
	public void putOnScreen(Screen s, String from, String msg) {
		
	}
	
	private List<FsNode> loadThemes() {
    	List<FsNode> collections = Fs.getNodes(themeBaseUri,1);
    	
    	List<FsNode> themes = new ArrayList<FsNode>();
    	for (FsNode collection: collections) {
    			themes.add(collection); 	
    			System.out.println("theme "+collection.getProperty("title"));
    	}
    	return themes;
    }
}
