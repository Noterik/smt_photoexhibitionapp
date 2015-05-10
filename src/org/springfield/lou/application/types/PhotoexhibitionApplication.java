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
//		for testing only should be changed afterwards
		
    	role = role == null ? "" : role;
		Capabilities caps = s.getCapabilities();
		int device = caps.getDeviceMode();
		
		if (device != caps.MODE_GENERIC || role.equals("mobile")) {
			//second screens
			loadStyleSheet(s,"mobile");
			s.setRole("mainscreen");
			this.loadContent(s, "secondscreen");
			
			loadThemes();				
			
			loadMapPage(s);
			
		} else {
			//main screens
			loadStyleSheet(s,"generic");
			s.setRole("mainscreen");
			this.loadContent(s, "mainscreen");
			
//			loadMapPage(s);
			
		}
		
			
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
	
	public void loadMapPage(Screen s){
		loadStyleSheet(s, "mapStyles");
		String mapScreen = s.getParameter("mapScreen");
		String screenSetup = s.getParameter("screenSetup");
		
    	mapScreen = mapScreen == null ? "" : mapScreen;
    	screenSetup = screenSetup == null ? "green" : screenSetup;
    	
		if(mapScreen!=null && "true".equals(mapScreen)){
			this.removeContent(s,"mainArea");
			this.loadContent(s, "mapPage");	
			switchToScreen(s);
			
		} 
	}
	
	
	public void switchToScreen(Screen s) {
			s.setDiv("green", "bind:mousedown", "greenPage", this);
			s.setDiv("red", "bind:mousedown", "redPage", this);
			s.setDiv("blue", "bind:mousedown", "bluePage", this);
	}
	
	
	public void greenPage(Screen s, String name) {
		s.removeContent("mapPage");
		this.loadContent(s,"greenPage");
		s.setDiv("returnToMainScreen", "bind:mousedown", "removeGreenPage", this);
	
	}
	
	public void redPage(Screen s, String name) {
		s.removeContent("mapPage");
		this.loadContent(s,"redPage");
		s.setDiv("returnToMainScreen", "bind:mousedown", "removeRedPage", this);
	}
	
	
	
	public void bluePage(Screen s, String name) {
		s.removeContent("mapPage");
		this.loadContent(s,"bluePage");
		s.setDiv("returnToMainScreen", "bind:mousedown", "removeBluePage", this);
	
	}
	
	public void removeGreenPage(Screen s, String name){
		s.removeContent("greenPage");
		this.loadContent(s,"mapPage");
		switchToScreen(s);
	}
	
	public void removeRedPage(Screen s, String name){
		s.removeContent("redPage");
		this.loadContent(s,"mapPage");
		switchToScreen(s);
	}
	
	public void removeBluePage(Screen s, String name){
		s.removeContent("bluePage");
		this.loadContent(s,"mapPage");
		switchToScreen(s);
	}

}
