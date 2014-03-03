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
package com.liferay.portal.kernel.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Since the Liferay CDI Portlet Bridge has a dependency on the Liferay Portal
 * logging API, this class is necessary to provide a compatibility layer with
 * the Java Logging API.
 * 
 * @author Neil Griffin
 */
public class LogImpl implements Log {

	// Private Data Members
	private Logger logger;

	public LogImpl(Class<?> clazz) {
		logger = Logger.getLogger(clazz.getName());
	}

	public LogImpl(String className) {
		logger = Logger.getLogger(className);
	}

	public void debug(Object msg) {
		logger.log(Level.FINE, msg.toString());
	}

	public void debug(Throwable t) {
		logger.log(Level.FINE, t.getMessage(), t);
	}

	public void debug(Object msg, Throwable t) {
		logger.log(Level.FINE, msg.toString(), t);
	}

	public void error(Object msg) {
		logger.log(Level.SEVERE, msg.toString());
	}

	public void error(Throwable t) {
		logger.log(Level.SEVERE, t.getMessage(), t);
	}

	public void error(Object msg, Throwable t) {
		logger.log(Level.SEVERE, msg.toString(), t);
	}

	public void fatal(Object msg) {
		logger.log(Level.SEVERE, msg.toString());
	}

	public void fatal(Throwable t) {
		logger.log(Level.SEVERE, t.getMessage(), t);
	}

	public void fatal(Object msg, Throwable t) {
		logger.log(Level.SEVERE, msg.toString(), t);
	}

	public void info(Object msg) {
		logger.info(msg.toString());
	}

	public void info(Throwable t) {
		logger.log(Level.INFO, t.getMessage(), t);
	}

	public void info(Object msg, Throwable t) {
		logger.log(Level.INFO, msg.toString(), t);
	}

	public void trace(Object msg) {
		logger.log(Level.FINEST, msg.toString());
	}

	public void trace(Throwable t) {
		logger.log(Level.FINEST, t.getMessage(), t);
	}

	public void trace(Object msg, Throwable t) {
		logger.log(Level.FINEST, msg.toString(), t);
	}

	public void warn(Object msg) {
		logger.log(Level.WARNING, msg.toString());
	}

	public void warn(Throwable t) {
		logger.log(Level.WARNING, t.getMessage(), t);
	}

	public void warn(Object msg, Throwable t) {
		logger.log(Level.WARNING, msg.toString(), t);
	}

	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINE);
	}

	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	public boolean isFatalEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINEST);
	}

	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

}
