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

import com.angelocvti.contactsbook.exceptions.ContactNotFoundException;
import com.angelocvti.contactsbook.exceptions.DaoException;
import com.angelocvti.contactsbook.exceptions.DuplicateEmailException;
import com.angelocvti.contactsbook.model.Contact;
import com.angelocvti.contactsbook.util.Email;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of CRUD operations defined in {@link com.angelocvti.contactsbook.persistence.Dao}.
 *
 * @author Angelo Cavalcanti
 */
public final class ContactDao implements Dao<Contact> {
  private final Connection connection;

  private ContactDao(Connection connection) {
    this.connection = connection;
  }

  /**
   * Reads contact data with the given id.
   *
   * @param id The primary key of table 'contacts'.
   * @return {@link com.angelocvti.contactsbook.model.Contact} instance (if the contact with the
   *     given id is found, null otherwise) wrapped in an {@link java.util.Optional}.
   * @author Angelo Cavalcanti
   */
  public Optional<Contact> findById(final Long id) {
    Contact contact = null;
    try {
      PreparedStatement preparedStatement =
          this.connection.prepareStatement("SELECT * FROM contacts WHERE id = ?");
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resultSet.getDate("birthDate"));
        contact =
            Contact.builder()
                .withName(resultSet.getString("name"))
                .withEmail(Email.of(resultSet.getString("email")))
                .withAddress(resultSet.getString("address"))
                .withBirthdate(calendar)
                .build();
      }
      resultSet.close();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new DaoException(e);
    }
    return Optional.ofNullable(contact);
  }

  /**
   * Reads contact data with the given email.
   *
   * @param email {@link com.angelocvti.contactsbook.util.Email Email} instance with the email value
   *     from the column 'email' of table 'contacts'.
   * @return {@link com.angelocvti.contactsbook.model.Contact Contact} instance (if the contact with
   *     the given email is found, null otherwise) wrapped in an {@link java.util.Optional
   *     Optional}.
   * @author Angelo Cavalcanti
   */
  public Optional<Contact> findByEmail(final Email email) {
    Contact contact = null;
    try {
      PreparedStatement preparedStatement =
          this.connection.prepareStatement("SELECT * FROM contacts WHERE email = ?");
      preparedStatement.setString(1, email.toString());
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resultSet.getDate("birthDate"));
        contact =
            Contact.builder()
                .withId(Long.valueOf(resultSet.getString("id")))
                .withName(resultSet.getString("name"))
                .withEmail(Email.of(resultSet.getString("email")))
                .withAddress(resultSet.getString("address"))
                .withBirthdate(calendar)
                .build();
      }
      resultSet.close();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new DaoException(e);
    }
    return Optional.ofNullable(contact);
  }

  /**
   * Reads all contacts data.
   *
   * @return {@link java.util.List List} instance, with one {@link
   *     com.angelocvti.contactsbook.model.Contact Contact} instance for each contact in the table
   *     'contacts'.
   * @author Angelo Cavalcanti
   */
  public List<Contact> findAll() {
    List<Contact> contacts = new ArrayList<>();
    try {
      PreparedStatement preparedStatement =
          this.connection.prepareStatement("SELECT * FROM contacts");
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resultSet.getDate("birthDate"));
        Contact contact =
            Contact.builder()
                .withId(resultSet.getLong("id"))
                .withName(resultSet.getString("name"))
                .withEmail(Email.of(resultSet.getString("email")))
                .withAddress(resultSet.getString("address"))
                .withBirthdate(calendar)
                .build();
        contacts.add(contact);
      }
      resultSet.close();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new DaoException(e);
    }
    return contacts;
  }

  /**
   * Persist the contact data.
   *
   * @param contact {@link com.angelocvti.contactsbook.model.Contact Contact} instance containing
   *     the contact data.
   * @exception DuplicateEmailException if the e-Mail is already in use by another contact.
   * @author Angelo Cavalcanti
   */
  public void insert(final Contact contact) {
    Optional<Contact> contactOptional = findByEmail(contact.getEmail());

    if (contactOptional.isPresent()) {
      throw new DuplicateEmailException(
          "The email: " + contact.getEmail().toString() + " is being used by another contact.");
    }

    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "INSERT INTO contacts (name, email ,address, birthdate) VALUES (?,?,?,?)");
      preparedStatement.setString(1, contact.getName());
      preparedStatement.setString(2, contact.getEmail().toString());
      preparedStatement.setString(3, contact.getAddress());
      preparedStatement.setDate(4, new Date(contact.getBirthdate().getTimeInMillis()));
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  /**
   * Persist the contact data and return the id assigned to the contact.
   *
   * @param contact {@link com.angelocvti.contactsbook.model.Contact Contact} instance containing
   *     the contact data.
   * @exception DuplicateEmailException if the e-Mail is already in use by another contact.
   * @return id assigned to the contact.
   * @author Angelo Cavalcanti
   */
  public Long insertAndGetId(final Contact contact) {
    Optional<Contact> contactOptional = findByEmail(contact.getEmail());

    if (contactOptional.isPresent()) {
      throw new DuplicateEmailException(
          "The email: " + contact.getEmail().toString() + " is being used by another contact.");
    }

    Long id;

    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "INSERT INTO contacts (name, email ,address, birthdate) VALUES (?,?,?,?)",
              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, contact.getName());
      preparedStatement.setString(2, contact.getEmail().toString());
      preparedStatement.setString(3, contact.getAddress());
      preparedStatement.setDate(4, new Date(contact.getBirthdate().getTimeInMillis()));
      preparedStatement.execute();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        id = resultSet.getLong(1);
        preparedStatement.close();
      } else {
        throw new SQLException("No generated keys found.");
      }
    } catch (SQLException e) {
      throw new DaoException(e);
    }
    return id;
  }

  /**
   * Update the contact data.
   *
   * @param id The id of the contact that will be updated (the primary key of table 'contacts').
   * @param contact {@link com.angelocvti.contactsbook.model.Contact Contact} instance containing
   *     the contact new data.
   * @exception ContactNotFoundException if no contacts were found with the given id.
   * @exception DuplicateEmailException if the e-Mail is already in use by another contact.
   * @author Angelo Cavalcanti
   */
  public void update(final Long id, final Contact contact) {
    Optional<Contact> contactOptional = findById(id);

    if (contactOptional.isEmpty()) {
      throw new ContactNotFoundException("No contact was found.");
    } else {
      Optional<Contact> persistedContactWithSameEmail = findByEmail(contact.getEmail());
      if (persistedContactWithSameEmail.isPresent()
          && !persistedContactWithSameEmail.get().getId().equals(id)) {
        throw new DuplicateEmailException(
            "The email: " + contact.getEmail() + " is being used by another contact.");
      }
    }

    try {
      PreparedStatement preparedStatement =
          this.connection.prepareStatement(
              "UPDATE contacts SET name = ?, email = ?, address = ?, birthdate = ? WHERE id = ?");
      preparedStatement.setString(1, contact.getName());
      preparedStatement.setString(2, contact.getEmail().toString());
      preparedStatement.setString(3, contact.getAddress());
      preparedStatement.setDate(4, new Date(contact.getBirthdate().getTimeInMillis()));
      preparedStatement.setLong(5, id);
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  /**
   * Delete the contact data.
   *
   * @param id The primary key of table 'contacts'.
   * @exception ContactNotFoundException if no contacts were found with the given id.
   * @author Angelo Cavalcanti
   */
  public void delete(final Long id) {
    Optional<Contact> contactOptional = findById(id);

    if (contactOptional.isEmpty()) {
      throw new ContactNotFoundException("No contact was found.");
    }

    try {
      PreparedStatement preparedStatement =
          this.connection.prepareStatement("DELETE FROM contacts WHERE id= ?");
      preparedStatement.setLong(1, id);
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  public static ContactDaoBuilder builder() {
    return new ContactDaoBuilder();
  }

  public static class ContactDaoBuilder {
    private Connection connection;

    private ContactDaoBuilder() {}

    public ContactDaoBuilder withConnection(Connection connection) {
      this.connection = Objects.requireNonNull(connection, "Connection is required.");
      return this;
    }

    public ContactDao build() {
      Objects.requireNonNull(connection, "Connection is required.");
      return new ContactDao(this.connection);
    }
  }
}
