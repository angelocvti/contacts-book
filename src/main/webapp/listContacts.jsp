<%--
  ~ MIT License
  ~
  ~ Copyright (c) 2020 Angelo Hugo Cavalcanti de Carvalho Silva
  ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a copy
  ~ of this software and associated documentation files (the "Software"), to deal
  ~ in the Software without restriction, including without limitation the rights
  ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  ~ copies of the Software, and to permit persons to whom the Software is
  ~ furnished to do so, subject to the following conditions:
  ~
  ~ The above copyright notice and this permission notice shall be included in all
  ~ copies or substantial portions of the Software.
  ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  ~ SOFTWARE.
 --%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Contacts list</title>
    <link
            href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css"
            rel="stylesheet"
    />
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
            crossorigin="anonymous"
    />
    <link href="css/background.css" rel="stylesheet"/>
    <link href="css/listContacts.css" rel="stylesheet"/>
    <link href="css/footer.css" rel="stylesheet"/>
</head>

<body id="page_body">
<core:import url="header.html"/>

<div
        id="modal_update_contact"
        class="modal fade"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabindex="-1"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modal title</h5>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <div class="modal-body">
                <form id="update_contact_form" class="text-center" action="updateContact">
                    <div class="form-floating mb-3">
                        <input
                                id="name_input"
                                type="text"
                                class="form-control"
                                placeholder="Name"
                                name="name"
                                required
                        />
                        <label for="name_input">Name</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input
                                id="email_input"
                                type="email"
                                class="form-control"
                                placeholder="e-Mail"
                                name="email"
                                required
                        />
                        <label for="email_input">e-Mail</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input
                                id="address_input"
                                type="text"
                                class="form-control"
                                placeholder="Address"
                                name="address"
                                required
                        />
                        <label for="address_input">Address</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input
                                id="birthdate_input"
                                type="text"
                                class="form-control"
                                placeholder="Birthdate"
                                name="birthdate"
                                required
                        />
                        <label for="birthdate_input">Birthdate</label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                >
                    Close
                </button>
                <button type="submit" form="update_contact_form" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div
        id="modal_delete_contact"
        class="modal fade"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabindex="-1"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Delete contact</h5>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <div id="modal_delete_contact_body" class="modal-body"></div>
            <div class="modal-footer">
                <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                >
                    Close
                </button>
                <button type="submit" form="delete_contact_form" class="btn btn-danger">Delete</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div id="div_row_contacts_table" class="row">
        <div class="table-responsive-md">
            <table
                    class="table table-hover table-bordered table-striped table-dark text-center"
            >
                <caption>
                    Contact information
                </caption>

                <thead class="thead-dark">
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">e-Mail</th>
                    <th scope="col">Address</th>
                    <th scope="col">Birthdate</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>

                <tbody>
                <core:forEach var="contact" items="${contacts}">
                    <tr>
                        <td>
                            <span id="contact_${contact.id}_name">${contact.name}</span>
                        </td>

                        <td>
                            <a
                                    id="contact_${contact.id}_email"
                                    href="mailto:${contact.email}"
                            >${contact.email}</a
                            >
                        </td>

                        <td>
                    <span id="contact_${contact.id}_address"
                    >${contact.address}</span
                    >
                        </td>

                        <td>
                    <span id="contact_${contact.id}_birthdate">
                      <fmt:formatDate
                              value="${contact.birthdate.time}"
                              pattern="dd/MM/yyyy"
                      />
                    </span>
                        </td>

                        <td>
                            <button
                                    id="update_contact_${contact.id}_button"
                                    data-contact-id="${contact.id}"
                                    class="btn btn-secondary"
                                    data-bs-toggle="modal"
                                    data-bs-target="#modal_update_contact"
                                    onclick="updateModalUpdateContactBody(this)"
                            >
                                Update
                            </button>
                            <button
                                    id="delete_contact_${contact.id}_button"
                                    data-contact-id="${contact.id}"
                                    class="btn btn-danger"
                                    data-bs-toggle="modal"
                                    data-bs-target="#modal_delete_contact"
                                    onclick="updateModalDeleteContactBody(this)"
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                </core:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<core:import url="footer.html"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"
></script>
<script src="js/calendar.js"></script>
<script src="js/listContacts.js"></script>
</body>
</html>
