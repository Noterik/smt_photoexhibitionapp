/* 
* Theme.java
* 
* Copyright (c) 2015 Noterik B.V.
* 
* This file is part of smt_photoexhibitionapp, related to the Noterik Springfield project.
*
* smt_photoexhibitionapp is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* smt_photoexhibitionapp is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with smt_photoexhibitionapp.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.springfield.lou.application.types;

import java.util.Iterator;
import java.util.List;

import org.springfield.fs.Fs;
import org.springfield.fs.FsNode;

/**
 * Theme.java
 *
 * @author Pieter van Leeuwen
 * @copyright Copyright: Noterik B.V. 2015
 * @package org.springfield.lou.application.types
 * 
 */
public class Theme {
	
	public ThemeTypeOne[] typeOne;
	public ThemeTypeTwo[] typeTwo;
	public ThemeTypeThree[] typeThree;
	
	public Theme() {
		//constructor
	}
	
	public Theme(FsNode theme) {
		System.out.println("getting theme"+theme.getPath());
		
		String path = theme.getPath().substring(0, theme.getPath().lastIndexOf("//"))+"/"+theme.getId();
		
		//read type one
		getTypeOne(path);
		//read type two
		getTypeTwo(path);
		//read type three
		getTypeThree(path);
	}
	
	private void getTypeOne(String path) {
		List<FsNode> typeones = Fs.getNodes(path+"/typeone", 1);
		
		System.out.println("get "+path+"/typeone");
		
		Iterator<FsNode> iterator = typeones.iterator();
		int size = 0;
		
		while (iterator.hasNext()) {
			iterator.next();
			size++;
		}
		
		typeOne = new ThemeTypeOne[size];
		
		iterator = typeones.iterator();
		int i = 0;
		
		while (iterator.hasNext()) {
			FsNode node = iterator.next();
			typeOne[i] = new ThemeTypeOne(node);
			i++;
 		}	
	}
	
	private void getTypeTwo(String path) {
		List<FsNode> typetwos = Fs.getNodes(path+"/typetwo", 1);
		
		System.out.println("get "+path+"/typetwo");
		
		Iterator<FsNode> iterator = typetwos.iterator();
		int size = 0;
		
		while (iterator.hasNext()) {
			iterator.next();
			size++;
		}
		
		typeTwo = new ThemeTypeTwo[size];
		
		iterator = typetwos.iterator();
		int i = 0;
		
		while (iterator.hasNext()) {
			FsNode node = iterator.next();
			typeTwo[i] = new ThemeTypeTwo(node);
			i++;
 		}
	}
	
	private void getTypeThree(String path) {
		List<FsNode> typethrees = Fs.getNodes(path+"/typethree", 1);
		
		System.out.println("get "+path+"/typethree");
		
		Iterator<FsNode> iterator = typethrees.iterator();
		int size = 0;
		
		while (iterator.hasNext()) {
			iterator.next();
			size++;
		}
		
		typeThree = new ThemeTypeThree[size];
		
		iterator = typethrees.iterator();
		int i = 0;
		
		while (iterator.hasNext()) {
			FsNode node = iterator.next();
			typeThree[i] = new ThemeTypeThree(node);
			i++;
 		}	
	}
}
