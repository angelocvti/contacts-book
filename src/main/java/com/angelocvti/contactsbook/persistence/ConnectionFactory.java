/*
 * MIT License
 *
 * Copyright (c) 2020 Angelo Hugo Cavalcanti de Carvalho Silva
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.angelocvti.contactsbook.persistence;

import com.angelocvti.contactsbook.exceptions.ConnectionFactoryException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** @author Angelo Cavalcanti */
public enum ConnectionFactory {
  INSTANCE;

  public Connection getConnection() {
    try {
      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection("jdbc:postgresql://localhost/agenda", "postgres", "admin");
    } catch (SQLException | ClassNotFoundException e) {
      throw new ConnectionFactoryException(e);
    }
  }
}
