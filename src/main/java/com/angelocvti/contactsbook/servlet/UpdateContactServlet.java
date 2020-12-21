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

package com.angelocvti.contactsbook.servlet;

import com.angelocvti.contactsbook.model.Contact;
import com.angelocvti.contactsbook.persistence.ConnectionFactory;
import com.angelocvti.contactsbook.persistence.ContactDao;
import com.angelocvti.contactsbook.util.Email;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** @author Angelo Cavalcanti */
@WebServlet(
    name = "UpdateContact",
    urlPatterns = {"/updateContact"})
public class UpdateContactServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Calendar birthdate;
    try {
      Date date = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("birthdate"));
      birthdate = Calendar.getInstance();
      birthdate.setTime(date);
    } catch (ParseException e) {
      throw new MalformedParametersException("Error parsing birthdate parameter.");
    }

    ContactDao.builder()
        .withConnection(ConnectionFactory.INSTANCE.getConnection())
        .build()
        .update(
            Long.valueOf(request.getParameter("id")),
            Contact.builder()
                .withName(request.getParameter("name"))
                .withEmail(Email.of(request.getParameter("email")))
                .withAddress(request.getParameter("address"))
                .withBirthdate(birthdate)
                .build());

    response.sendRedirect(request.getContextPath() + "/listContact");
  }
}
