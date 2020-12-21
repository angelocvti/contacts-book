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

function updateModalUpdateContactBody(element) {
  const name = document.getElementById(
    "contact_" + element.dataset.contactId + "_name"
  ).innerText;
  const email = document.getElementById(
    "contact_" + element.dataset.contactId + "_email"
  ).innerText;
  const address = document.getElementById(
    "contact_" + element.dataset.contactId + "_address"
  ).innerText;
  const birthdate = document.getElementById(
    "contact_" + element.dataset.contactId + "_birthdate"
  ).innerText;
  document.getElementById("name_input").value = name;
  document.getElementById("email_input").value = email;
  document.getElementById("address_input").value = address;
  document.getElementById("birthdate_input").value = birthdate;

  const hiddenInput = document.createElement("input");
  hiddenInput.setAttribute("name", "id");
  hiddenInput.style.display = "none";
  hiddenInput.value = element.dataset.contactId;

  document.getElementById("update_contact_form").appendChild(hiddenInput);
}

function updateModalDeleteContactBody(element) {
  const modalDeleteContactBody = document.getElementById(
    "modal_delete_contact_body"
  );

  const message =
    "Delete " +
    document.getElementById("contact_" + element.dataset.contactId + "_name")
      .innerText +
    "'s contact?";
  modalDeleteContactBody.innerHTML = message;

  const deleteContactForm = document.createElement("form");
  deleteContactForm.setAttribute("id", "delete_contact_form");
  deleteContactForm.setAttribute("action", "deleteContact");

  const hiddenInput = document.createElement("input");
  hiddenInput.setAttribute("name", "id");
  hiddenInput.style.display = "none";
  hiddenInput.value = element.dataset.contactId;

  deleteContactForm.appendChild(hiddenInput);
  modalDeleteContactBody.appendChild(deleteContactForm);
}
