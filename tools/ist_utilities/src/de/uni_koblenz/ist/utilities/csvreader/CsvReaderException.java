/*
 * IST Utilities
 * (c) 2006-2009 Institute for Software Technology
 *               University of Koblenz-Landau, Germany
 *
 *               ist@uni-koblenz.de
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package de.uni_koblenz.ist.utilities.csvreader;

/**
 * @author riediger
 */
public class CsvReaderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a CsvReaderException. 
	 */
	public CsvReaderException() {
		super();
	}

	/**
	 * Creates a CsvReaderException with a specified error message.
	 * 
	 * @param message a String containing the error message
	 */
	public CsvReaderException(String message) {
		super(message);
	}

	/**
	 * Creates a CsvReaderException with a specified error message and a nested exception.
	 * 
	 * @param message a String containing the error message
	 * @param cause nested exception as reason for this exception
	 */
	public CsvReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a CsvReaderException with a nested exception.
	 * 
	 * @param cause nested exception as reason for this exception
	 */
	public CsvReaderException(Throwable cause) {
		super(cause);
	}

}
