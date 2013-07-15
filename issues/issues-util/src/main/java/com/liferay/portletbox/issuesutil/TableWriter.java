/**
 * Software License Agreement (BSD License)
 * 
 * Copyright (c) 2013, Liferay Inc.
 * All rights reserved.
 * 
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 * 
 * * The name of Liferay Inc. may not be used to endorse or promote products
 *   derived from this software without specific prior
 *   written permission of Liferay Inc.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.liferay.portletbox.issuesutil;

import java.io.PrintWriter;

/**
 * 
 * @author Scott Nicklous
 * 
 * Class to write URLs and buttons in a table into the output stream.
 *
 */

public class TableWriter {

   final int NUMBER_OF_COLUMNS = 3;
   int colCounter = 0;
   int numColumns = NUMBER_OF_COLUMNS;
   PrintWriter writer = null;

   /**
    * Constructs a new TableWriter with the default number of columns.
    * @param writer
    */
   public TableWriter (PrintWriter writer) {
      this.writer = writer;
      this.numColumns = NUMBER_OF_COLUMNS;
   }

   /**
    * Constructs a new TableWriter specifying the desired number of columns.
    * @param writer
    * @param numColumns
    */
   public TableWriter (PrintWriter writer, int numColumns) {
      this.writer = writer;
      this.numColumns = numColumns;
   }

   /**
    * Adds HTML markup to begin the table.
    */
   public void startTable () {
      colCounter = 0;
      writer.write("<table border='0' cellpadding='4'><tr>");
   }

   /**
    * Adds HTML markup to close the table.
    */
   public void endTable () {
      writer.write("</tr></table>");
      writer.write(HTMLUtil.HR_TAG);
   }

   /**
    * Adds a new URL to the table.
    * 
    * @param comment       - name of the URL
    * @param urlAsString   
    */
   public void writeURL(String comment, String urlAsString) {
      writer.write("<td>");
      writer.write("<a href=\"");
      writer.write(urlAsString);
      writer.write("\">");
      writer.write(comment);
      writer.write(HTMLUtil.BR_TAG);
      writer.write("</a>");
      writer.write("</td>");
      if (++colCounter % numColumns == 0) {
         writer.write("</tr><tr>");
      }
   }

   /**
    * Adds a new button to the table.
    * 
    * @param comment       - name of the button
    * @param urlAsString
    */
   public void writeButton(String comment, String urlAsString) {
      writer.write("<td><form action=\"" + urlAsString + "\" method=\"post\">");
      writer.write("<input type=\"submit\" value=\"" + comment + "\" />");
      writer.write("</form></td>");
      if (++colCounter % numColumns == 0) {
         writer.write("</tr><tr>");
      }
   }

}

