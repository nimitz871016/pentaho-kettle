/* ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.steps.oldtextfileinput;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.job.entry.loadSave.TransStepLoadSaveTester;
import org.pentaho.di.trans.steps.loadsave.validator.ArrayLoadSaveValidator;
import org.pentaho.di.trans.steps.loadsave.validator.FieldLoadSaveValidator;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * @author Andrey Khayrutdinov
 */
public class OldTextFileInputMetaLoadSaveTest {

  private TransStepLoadSaveTester<OldTextFileInputMeta> tester;

  @Before
  public void setUp() throws Exception {
    List<String> commonAttributes = Arrays.asList(
      "fileName",
      "fileType",
      "separator",
      "enclosure",
      "escapeCharacter",
      "breakInEnclosureAllowed",
      "header",
      "nrHeaderLines",
      "footer",
      "nrFooterLines",
      "lineWrapped",
      "nrWraps",
      "layoutPaged",
      "nrLinesDocHeader",
      "nrLinesPerPage",
      "fileCompression",
      "noEmptyLines",
      "includeFilename",
      "filenameField",
      "includeRowNumber",
      "rowNumberByFile",
      "rowNumberField",
      "fileFormat",
      "rowLimit",
      "inputFields",
      "filter",
      "encoding",
      "errorIgnored",
      "errorCountField",
      "errorFieldsField",
      "errorTextField",
      "warningFilesDestinationDirectory",
      "warningFilesExtension",
      "errorFilesDestinationDirectory",
      "errorFilesExtension",
      "lineNumberFilesDestinationDirectory",
      "lineNumberFilesExtension",
      "dateFormatLenient",
      "dateFormatLocale",
      "errorLineSkipped",
      "acceptingFilenames",
      "passingThruFields",
      "acceptingField",
      "isaddresult",
      "shortFileFieldName",
      "pathFieldName",
      "hiddenFieldName",
      "lastModificationTimeFieldName",
      "uriNameFieldName",
      "rootUriNameFieldName",
      "extensionFieldName",
      "sizeFieldName",
      "skipBadFiles",
      "fileErrorField",
      "fileErrorMessageField"
    );
    List<String> xmlAttributes = Collections.emptyList();
    List<String> repoAttributes = Collections.emptyList();

    Map<String, String> getters = new HashMap<String, String>(  );
    getters.put( "header", "hasHeader" );
    getters.put( "footer", "hasFooter" );
    getters.put( "noEmptyLines", "noEmptyLines" );
    getters.put( "includeFilename", "includeFilename" );
    getters.put( "includeRowNumber", "includeRowNumber" );
    getters.put( "errorFilesExtension", "getErrorLineFilesExtension" );
    getters.put( "isaddresult", "isAddResultFile" );
    getters.put( "shortFileFieldName", "getShortFileNameField" );
    getters.put( "pathFieldName", "getPathField" );
    getters.put( "hiddenFieldName", "isHiddenField" );
    getters.put( "lastModificationTimeFieldName", "getLastModificationDateField" );
    getters.put( "uriNameFieldName", "getUriField" );
    getters.put( "rootUriNameFieldName", "getRootUriField" );
    getters.put( "extensionFieldName", "getExtensionField" );
    getters.put( "sizeFieldName", "getSizeField" );

    Map<String, String> setters = new HashMap<String, String>(  );
    setters.put( "fileName", "setFileNameForTest" );
    setters.put( "errorFilesExtension", "setErrorLineFilesExtension" );
    setters.put( "isaddresult", "setAddResultFile" );
    setters.put( "shortFileFieldName", "setShortFileNameField" );
    setters.put( "pathFieldName", "setPathField" );
    setters.put( "hiddenFieldName", "setIsHiddenField" );
    setters.put( "lastModificationTimeFieldName", "setLastModificationDateField" );
    setters.put( "uriNameFieldName", "setUriField" );
    setters.put( "rootUriNameFieldName", "setRootUriField" );
    setters.put( "extensionFieldName", "setExtensionField" );
    setters.put( "sizeFieldName", "setSizeField" );

    Map<String, FieldLoadSaveValidator<?>> attributeValidators = Collections.emptyMap();

    Map<String, FieldLoadSaveValidator<?>> typeValidators = new HashMap<String, FieldLoadSaveValidator<?>>(  );
    typeValidators.put( OldTextFileFilter[].class.getCanonicalName(), new ArrayLoadSaveValidator<OldTextFileFilter>( new TextFileFilterValidator() ) );
    typeValidators.put( TextFileInputField[].class.getCanonicalName(), new ArrayLoadSaveValidator<TextFileInputField>( new TextFileInputFieldValidator() ) );

    assertTrue( !commonAttributes.isEmpty() || !( xmlAttributes.isEmpty() || repoAttributes.isEmpty() ) );

    tester =
        new TransStepLoadSaveTester<OldTextFileInputMeta>( OldTextFileInputMeta.class, commonAttributes, xmlAttributes,
            repoAttributes, getters, setters, attributeValidators, typeValidators );
  }

  @Test
  public void xmlSerialization() throws Exception {
    tester.testXmlRoundTrip();
  }

  @Test
  public void repositorySerialization() throws Exception {
    tester.testRepoRoundTrip();
  }


  private static class TextFileInputFieldValidator implements FieldLoadSaveValidator<TextFileInputField> {
    @Override public TextFileInputField getTestObject() {
      return new TextFileInputField( UUID.randomUUID().toString(), new Random().nextInt(), new Random().nextInt() );
    }

    @Override
    public boolean validateTestObject( TextFileInputField testObject, Object actual ) {
      if ( !( actual instanceof TextFileInputField ) ) {
        return false;
      }

      TextFileInputField another = (TextFileInputField) actual;
      return new EqualsBuilder()
        .append( testObject.getName(), another.getName() )
        .append( testObject.getLength(), another.getLength() )
        .append( testObject.getPosition(), another.getPosition() )
        .isEquals();
    }
  }

  private static class TextFileFilterValidator implements FieldLoadSaveValidator<OldTextFileFilter> {
    @Override public OldTextFileFilter getTestObject() {
      OldTextFileFilter fileFilter = new OldTextFileFilter();
      fileFilter.setFilterPosition( new Random().nextInt() );
      fileFilter.setFilterString( UUID.randomUUID().toString() );
      fileFilter.setFilterLastLine( new Random().nextBoolean() );
      fileFilter.setFilterPositive( new Random().nextBoolean() );
      return fileFilter;
    }

    @Override public boolean validateTestObject( OldTextFileFilter testObject, Object actual ) {
      if ( !( actual instanceof OldTextFileFilter ) ) {
        return false;
      }

      OldTextFileFilter another = (OldTextFileFilter) actual;
      return new EqualsBuilder()
        .append( testObject.getFilterPosition(), another.getFilterPosition() )
        .append( testObject.getFilterString(), another.getFilterString() )
        .append( testObject.isFilterLastLine(), another.isFilterLastLine() )
        .append( testObject.isFilterPositive(), another.isFilterPositive() )
        .isEquals();
    }
  }
}
