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
import java.util.Date;
import java.util.Iterator;
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

	private int currentTheme = 0;
	private int currentTypeOne = 0;
	private ArrayList<Screen> typeOneMainscreens;
	private int currentTypeTwo = 0;
	private ArrayList<Screen> typeTwoMainscreens;
	private int currentTypeThree = 0;
	private ArrayList<Screen> typeThreeMainscreens;
	
	private List<Theme> themes;
	
	private static final int MAX_TYPE_ONE_SCREENS = 3;
	private static final int MAX_TYPE_TWO_SCREENS = 1;
	private static final int MAX_TYPE_THREE_SCREENS = 6;
 	private static final String themeBaseUri = "/domain/espace/user/photoexhibition/collection/";
	
	public PhotoexhibitionApplication(String id) {
		super(id);
		
		themes = loadThemes();
		
		typeOneMainscreens = new ArrayList<Screen>(MAX_TYPE_ONE_SCREENS);
		typeTwoMainscreens = new ArrayList<Screen>(MAX_TYPE_TWO_SCREENS);
		typeThreeMainscreens =  new ArrayList<Screen>(MAX_TYPE_THREE_SCREENS);
	}
	
	public void onNewScreen(Screen s) {
		String role = s.getParameter("role");
		String type = s.getParameter("type");
		
    	role = role == null ? "" : role;
    	type = type == null ? "" : type;
    	
		Capabilities caps = s.getCapabilities();
		int device = caps.getDeviceMode();
		
		if ((device != caps.MODE_GENERIC && !role.equals("main")) || role.equals("mobile")) {
			//second screens
			loadStyleSheet(s,"mobile");
			s.setRole("mainscreen");
			this.loadContent(s, "secondscreen");
			
			loadThemes();				
			
			loadMapPage(s);
		} else {
			//main screens
			long id = new Date().getTime();
			
			if (type.equals("one")) {
				if (typeOneMainscreens.size() < MAX_TYPE_ONE_SCREENS) {
					typeOneMainscreens.add(s);
					
					loadStyleSheet(s,"generic");
					s.setRole("mainscreenone");
					s.setProperty("screen", String.valueOf(id));
					this.loadContent(s, "mainscreen");
					
					ThemeTypeOne[] typeOne = themes.get(currentTheme).typeOne;
					
					System.out.println("get for type one "+typeOne.length);
					
					if (currentTypeOne >= typeOne.length-1) {
						currentTypeOne = 0;
					}
					
					ThemeTypeOne current = typeOne[currentTypeOne];
					
					System.out.println("current for type one "+currentTypeOne);
					
					long time = new Date().getTime();
					
					s.setContent("photo", "<img id=\"image"+String.valueOf(time)+"\" src=\""+current.image+"\">");
					s.setDiv("image"+String.valueOf(time),"bind:load","imageloaded",this);
					
					currentTypeOne++;
				}
			} else if (type.equals("two")) {
				if (typeTwoMainscreens.size() < MAX_TYPE_TWO_SCREENS) {
						typeTwoMainscreens.add(s);
					
						loadStyleSheet(s,"generic");
						s.setRole("mainscreentwo");
						s.setProperty("screen", String.valueOf(id));
						this.loadContent(s, "mainscreen");
						
						ThemeTypeTwo[] typeTwo = themes.get(currentTheme).typeTwo;
						
						ThemeTypeTwo current = typeTwo[currentTypeTwo];
						
						s.setContent("photo", "<img id=\"image\" src=\""+current.image+"\">");
						s.setDiv("image","bind:load","imageloaded",this);
						
						currentTypeTwo++;
				}
			} else if (type.equals("three")) {
				if (typeThreeMainscreens.size() < MAX_TYPE_THREE_SCREENS) {
					typeThreeMainscreens.add(s);
					
					loadStyleSheet(s,"generic");
					s.setRole("mainscreenthree");
					s.setProperty("screen", String.valueOf(id));
					this.loadContent(s, "mainscreen");
					
					ThemeTypeThree[] typeThree = themes.get(currentTheme).typeThree;
					
					ThemeTypeThree current = typeThree[currentTypeThree];
					
					s.setContent("photo", "<img id=\"image\" src=\""+current.image+"\">");
					s.setDiv("image","bind:load","imageloaded",this);
					
					currentTypeThree++;
				}
			}
		}
	}
	
	//triggered when image is fully loaded, signals back to resize image on main screen
	public void imageloaded(Screen s, String name) {
		s.putMsg("mainscreen", "app", "resizeImage()");
	}
	
	public void previousImage(Screen s, String name) {
		System.out.println("Requesting previous image");
		
		currentTypeOne -= typeOneMainscreens.size()*2;
		
		if (currentTypeOne < 0) {
			currentTypeOne = themes.get(currentTheme).typeOne.length-typeOneMainscreens.size();
		} 
		
		ThemeTypeOne[] typeOne = themes.get(currentTheme).typeOne;
		
		for (Iterator<Screen> iterator = typeOneMainscreens.iterator(); iterator.hasNext(); ) {
			Screen typeonescreen = iterator.next();

			if (currentTypeOne >= themes.get(currentTheme).typeOne.length) {
				currentTypeOne = 0;
			}
			
			long time = new Date().getTime();
			System.out.println("current image "+currentTypeOne);
			
			ThemeTypeOne current = typeOne[currentTypeOne];
			
			typeonescreen.setContent("photo", "<img id=\"image"+String.valueOf(time)+"\" src=\""+current.image+"\">");
			typeonescreen.setDiv("image"+String.valueOf(time),"bind:load","imageloaded",this);
			currentTypeOne++;
		}
	}
	
	public void nextImage(Screen s, String name) {
		System.out.println("Requesting next image");
		
		ThemeTypeOne[] typeOne = themes.get(currentTheme).typeOne;
		
		for (Iterator<Screen> iterator = typeOneMainscreens.iterator(); iterator.hasNext(); ) {
			Screen typeonescreen = iterator.next();
			
			if (currentTypeOne >= themes.get(currentTheme).typeOne.length) {
				currentTypeOne = 0;
			}
			long time = new Date().getTime();
			System.out.println("current image "+currentTypeOne);
			
			ThemeTypeOne current = typeOne[currentTypeOne];
			
			typeonescreen.setContent("photo", "<img id=\"image"+String.valueOf(time)+"\" src=\""+current.image+"\">");
			typeonescreen.setDiv("image"+String.valueOf(time),"bind:load","imageloaded",this);
			currentTypeOne++;
		}
	}
	
	private List<Theme> loadThemes() {
    	List<FsNode> collections = Fs.getNodes(themeBaseUri,1);
    	
    	List<Theme> themes = new ArrayList<Theme>();
    	for (FsNode collection: collections) {
    			Theme theme = new Theme(collection);
    			themes.add(theme);	
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
			//moved to themeselection
		} else {
			s.setDiv("theme1", "bind:mousedown", "themeSelection1", this);
			s.setDiv("theme2", "bind:mousedown", "themeSelection2", this);
			s.setDiv("theme3", "bind:mousedown", "themeSelection3", this);
		}
	}
	
	public void themeSelection1(Screen s, String name) {
		currentTheme = 0;
		updateMainScreens();
		
		this.removeContent(s,"mainArea");
		this.removeContent(s, "header");
		this.loadContent(s, "mapPage");	
		switchToScreen(s);
	}
	
	public void themeSelection2(Screen s, String name) {
		currentTheme = 1;
		updateMainScreens();
		
		this.removeContent(s,"mainArea");
		this.removeContent(s, "header");
		this.loadContent(s, "mapPage");	
		switchToScreen(s);
	}

	public void themeSelection3(Screen s, String name) {
		currentTheme = 2;
		updateMainScreens();
		
		this.removeContent(s,"mainArea");
		this.removeContent(s, "header");
		this.loadContent(s, "mapPage");	
		switchToScreen(s);
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
		s.setDiv("touchSwipe_left", "bind:click", "previousImage", this);
		s.setDiv("touchSwipe_right", "bind:click", "nextImage", this);
		
	}
	
	
	
	public void bluePage(Screen s, String name) {
		s.removeContent("mapPage");
		this.loadContent(s,"bluePage");
		s.setDiv("returnToMainScreen", "bind:mousedown", "removeBluePage", this);
		
		s.setDiv("cloud1Blue","bind:mousedown","sound1",this);
		s.setDiv("cloud2Blue","bind:mousedown","sound2",this);
		s.setDiv("cloud3Blue","bind:mousedown","sound3",this);
		s.setDiv("cloud4Blue","bind:mousedown","sound4",this);
		s.setDiv("cloud5Blue","bind:mousedown","sound5",this);
		s.setDiv("cloud6Blue","bind:mousedown","sound6",this);
		
	}
	
	public void sound1(Screen s, String name){
		s.setDiv("cloud1Blue","sound:parasites");
	}
	
	public void sound2(Screen s, String name){
		s.setDiv("cloud2Blue","sound:parasites");
	}
	public void sound3(Screen s, String name){
		s.setDiv("cloud3Blue","sound:parasites");
	}
	public void sound4(Screen s, String name){
		s.setDiv("cloud4Blue","sound:parasites");
	}
	public void sound5(Screen s, String name){
		s.setDiv("cloud5Blue","sound:parasites");
	}
	public void sound6(Screen s, String name){
		s.setDiv("cloud6Blue","sound:parasites");
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
	
	public void updateMainScreens() {
		System.out.println("Updating themes, theme selected = "+currentTheme+" total themes "+themes.size());
		
		currentTypeOne = 0;
	
		ThemeTypeOne[] typeOne = themes.get(currentTheme).typeOne;
		
		for (Iterator<Screen> iterator = typeOneMainscreens.iterator(); iterator.hasNext(); ) {
			Screen typeonescreen = iterator.next();
			
			if (currentTypeOne >= themes.get(currentTheme).typeOne.length) {
				currentTypeOne = 0;
			}
			long time = new Date().getTime();
			System.out.println("current image "+currentTypeOne);
			
			ThemeTypeOne current = typeOne[currentTypeOne];
			
			typeonescreen.setContent("photo", "<img id=\"image"+String.valueOf(time)+"\" src=\""+current.image+"\">");
			typeonescreen.setDiv("image"+String.valueOf(time),"bind:load","imageloaded",this);
			currentTypeOne++;
		}
		
		ThemeTypeTwo[] typeTwo = themes.get(currentTheme).typeTwo;
		
		for (Iterator<Screen> iterator = typeTwoMainscreens.iterator(); iterator.hasNext(); ) {
			Screen typetwoscreen = iterator.next();
			
			if (currentTypeTwo >= themes.get(currentTheme).typeTwo.length) {
				currentTypeTwo = 0;
			}
			long time = new Date().getTime();
			System.out.println("current image "+currentTypeTwo);
			
			ThemeTypeTwo currenttwo = typeTwo[currentTypeTwo];
			
			typetwoscreen.setContent("photo", "<img id=\"image"+String.valueOf(time)+"\" src=\""+currenttwo.image+"\">");
			System.out.println("Setting type two to "+currenttwo.image);
			typetwoscreen.setDiv("image"+String.valueOf(time),"bind:load","imageloaded",this);
			currentTypeThree++;
		}
		
		ThemeTypeThree[] typeThree = themes.get(currentTheme).typeThree;
		
		for (Iterator<Screen> iterator = typeThreeMainscreens.iterator(); iterator.hasNext(); ) {
			Screen typethreescreen = iterator.next();
			
			if (currentTypeThree >= themes.get(currentTheme).typeThree.length) {
				currentTypeThree = 0;
			}
			long time = new Date().getTime();
			System.out.println("current image "+currentTypeThree);
			
			ThemeTypeThree currentthree = typeThree[currentTypeThree];
			
			typethreescreen.setContent("photo", "<img id=\"image"+String.valueOf(time)+"\" src=\""+currentthree.image+"\">");
			typethreescreen.setDiv("image"+String.valueOf(time),"bind:load","imageloaded",this);
			currentTypeThree++;
		}
		
	}
}
