/* 
* ThemeTypeThree.java
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

import org.springfield.fs.FsNode;

/**
 * ThemeTypeThree.java
 *
 * @author Pieter van Leeuwen
 * @copyright Copyright: Noterik B.V. 2015
 * @package org.springfield.lou.application.types
 * 
 */
public class ThemeTypeThree {

	public String image;
	
	public ThemeTypeThree() {
		//constructor
	}
	
	public ThemeTypeThree(FsNode node) {
		image = node.getProperty("image", "");
	}
}
