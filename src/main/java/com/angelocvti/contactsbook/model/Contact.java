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

package com.angelocvti.contactsbook.model;

import java.util.Calendar;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

/**
 * Represents an contact.
 *
 * @author Angelo Cavalcanti
 */
public class Contact {

  private final Long id;

  @NotBlank
  private final String name;

  @NotNull
  @Email
  private final String email;

  @NotBlank
  private final String address;

  @NotNull
  @PastOrPresent
  private final Calendar birthdate;

  private Contact(Long id, String name, String email, String address, Calendar birthdate) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.address = address;
    this.birthdate = birthdate;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public Calendar getBirthdate() {
    return birthdate;
  }

  public static ContactBuilder builder() {
    return new ContactBuilder();
  }

  public static class ContactBuilder {

    private Long id;
    private String name;
    private String email;
    private String address;
    private Calendar birthdate;

    private ContactBuilder() {
    }

    public ContactBuilder withId(Long id) {
      this.id = Objects.requireNonNull(id, "Id is required.");
      return this;
    }

    public ContactBuilder withName(String name) {
      this.name = Objects.requireNonNull(name, "Name is required.");
      return this;
    }

    public ContactBuilder withEmail(String email) {
      this.email = Objects.requireNonNull(email, "e-Mail is required.");
      return this;
    }

    public ContactBuilder withAddress(String address) {
      this.address = Objects.requireNonNull(address, "Address is required.");
      return this;
    }

    public ContactBuilder withBirthdate(Calendar birthdate) {
      this.birthdate = Objects.requireNonNull(birthdate, "Birthdate is required.");

      if (birthdate.after(Calendar.getInstance())) {
        throw new IllegalArgumentException("Birthdate needs to be in the past.");
      }

      return this;
    }

    public Contact build() {
      Objects.requireNonNull(this.name, "Name is required.");
      Objects.requireNonNull(this.email, "e-Mail is required.");
      Objects.requireNonNull(this.address, "Address is required.");
      Objects.requireNonNull(this.birthdate, "Birthdate is required.");
      return new Contact(this.id, this.name, this.email, this.address, this.birthdate);
    }
  }
}
