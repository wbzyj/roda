/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/roda
 */

package org.roda.wui.client.planning;

import java.util.Date;

import org.roda.core.data.v2.formats.Format;
import org.roda.wui.client.common.IncrementalList;
import org.roda.wui.common.client.ClientLogger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;

import config.i18n.client.UserManagementConstants;

/**
 * @author Luis Faria
 *
 */
public class FormatDataPanel extends Composite implements HasValueChangeHandlers<Format> {

  interface MyUiBinder extends UiBinder<Widget, FormatDataPanel> {
  }

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @SuppressWarnings("unused")
  private static UserManagementConstants constants = (UserManagementConstants) GWT
    .create(UserManagementConstants.class);

  @UiField
  TextBox name;

  @UiField
  TextArea definition;

  @UiField
  TextBox category;

  @UiField
  TextBox latestVersion;

  @UiField
  IntegerBox popularity;

  @UiField
  TextBox developer;

  @UiField
  DateBox initialRelease;

  @UiField
  TextBox standard;

  @UiField
  CheckBox isOpenFormat;

  @UiField
  TextBox website;

  @UiField
  TextArea provenanceInformation;

  @UiField
  IncrementalList extensions;

  @UiField
  IncrementalList mimetypes;

  @UiField
  IncrementalList pronoms;

  @UiField
  IncrementalList utis;

  @SuppressWarnings("unused")
  private ClientLogger logger = new ClientLogger(getClass().getName());

  private boolean editmode;

  private boolean changed = false;
  private boolean checked = false;

  /**
   * Create a new user data panel
   *
   * @param editmode
   *          if user name should be editable
   */
  public FormatDataPanel(boolean editmode) {
    this(true, editmode);
  }

  /**
   * Create a new user data panel
   *
   * @param visible
   * @param editmode
   */
  public FormatDataPanel(boolean visible, boolean editmode) {
    initWidget(uiBinder.createAndBindUi(this));

    this.editmode = editmode;
    super.setVisible(visible);

    DefaultFormat dateFormat = new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd"));
    initialRelease.setFormat(dateFormat);
    initialRelease.getDatePicker().setYearArrowsVisible(true);
    initialRelease.setFireNullValues(true);

    ChangeHandler changeHandler = new ChangeHandler() {

      @Override
      public void onChange(ChangeEvent event) {
        FormatDataPanel.this.onChange();
      }
    };

    KeyUpHandler keyUpHandler = new KeyUpHandler() {

      @Override
      public void onKeyUp(KeyUpEvent event) {
        onChange();
      }
    };

    name.addChangeHandler(changeHandler);
    name.addKeyUpHandler(keyUpHandler);
    definition.addChangeHandler(changeHandler);
    definition.addKeyUpHandler(keyUpHandler);
    category.addChangeHandler(changeHandler);
    category.addKeyUpHandler(keyUpHandler);

    latestVersion.addChangeHandler(changeHandler);
    popularity.addChangeHandler(changeHandler);
    developer.addChangeHandler(changeHandler);

    initialRelease.addValueChangeHandler(new ValueChangeHandler<Date>() {

      @Override
      public void onValueChange(ValueChangeEvent<Date> event) {
        onChange();
      }
    });

    standard.addChangeHandler(changeHandler);
    website.addChangeHandler(changeHandler);
    provenanceInformation.addChangeHandler(changeHandler);

    extensions.addChangeHandler(changeHandler);
    mimetypes.addChangeHandler(changeHandler);
    pronoms.addChangeHandler(changeHandler);
    utis.addChangeHandler(changeHandler);
  }

  public boolean isValid() {
    boolean valid = true;

    if (name.getText().length() == 0) {
      valid = false;
      name.addStyleName("isWrong");
    } else {
      name.removeStyleName("isWrong");
    }

    if (initialRelease.getValue() == null || initialRelease.getValue().after(new Date())) {
      valid = false;
      initialRelease.addStyleName("isWrong");
    } else {
      initialRelease.removeStyleName("isWrong");
    }

    if (category.getText().length() == 0) {
      valid = false;
      category.addStyleName("isWrong");
    } else {
      category.removeStyleName("isWrong");
    }

    try {
      Integer.parseInt(popularity.getText());
      popularity.removeStyleName("isWrong");
    } catch (NumberFormatException e) {
      valid = false;
      popularity.addStyleName("isWrong");
    }

    checked = true;
    return valid;
  }

  public void setFormat(Format format) {
    this.name.setText(format.getName());
    this.definition.setText(format.getDefinition());
    this.category.setText(format.getCategory());
    this.latestVersion.setText(format.getLatestVersion());
    this.popularity.setValue(format.getPopularity());
    this.developer.setText(format.getDeveloper());
    this.initialRelease.setValue(format.getInitialRelease());
    this.standard.setText(format.getStandard());
    this.isOpenFormat.setValue(format.isOpenFormat());
    this.website.setText(format.getWebsite());
    this.provenanceInformation.setText(format.getProvenanceInformation());

    this.extensions.setTextBoxList(format.getExtensions());
    this.mimetypes.setTextBoxList(format.getMimetypes());
    this.pronoms.setTextBoxList(format.getPronoms());
    this.utis.setTextBoxList(format.getUtis());
  }

  public Format getFormat() {

    Format format = new Format();
    format.setName(name.getText());
    format.setDefinition(definition.getText());
    format.setCategory(category.getText());
    format.setLatestVersion(latestVersion.getText());
    format.setPopularity(popularity.getValue());
    format.setDeveloper(developer.getText());
    format.setInitialRelease(initialRelease.getValue());
    format.setStandard(standard.getText());
    format.setOpenFormat(isOpenFormat.getValue());
    format.setWebsite(website.getText());
    format.setProvenanceInformation(provenanceInformation.getText());

    format.setExtensions(extensions.getTextBoxesValue());
    format.setMimetypes(mimetypes.getTextBoxesValue());
    format.setPronoms(pronoms.getTextBoxesValue());
    format.setUtis(utis.getTextBoxesValue());

    return format;
  }

  public void clear() {
    name.setText("");
    definition.setText("");
    category.setText("");
    latestVersion.setText("");
    popularity.setValue(null);
    developer.setText("");
    standard.setText("");
    website.setText("");
    provenanceInformation.setText("");

    extensions.clearTextBoxes();
    mimetypes.clearTextBoxes();
    pronoms.clearTextBoxes();
    utis.clearTextBoxes();
  }

  /**
   * Is user data panel editable, i.e. on create user mode
   *
   * @return true if editable
   */
  public boolean isEditmode() {
    return editmode;
  }

  /**
   * Is user data panel has been changed
   *
   * @return changed
   */
  public boolean isChanged() {
    return changed;
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Format> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  protected void onChange() {
    changed = true;
    if (checked) {
      isValid();
    }
    ValueChangeEvent.fire(this, getValue());
  }

  public Format getValue() {
    return getFormat();
  }
}