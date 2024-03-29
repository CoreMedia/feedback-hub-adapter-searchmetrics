# The feedback-hub-adapter-searchmetrics Plugin Manual

## Table of Contents

1. [Installation](installation.md)

   How to administrate the feedback-hub-adapter-searchmetrics plugin (especially in CoreMedia Studio).

1. [Contribute](contribute.md)

   Read this if you are contributing to the project.

1. [Release](release.md)

   Read this if you are creating a release of this project.

## See Also

* [Used Third-Party Libraries](THIRD-PARTY.txt)

    <!-- GitHub Pages is not able to list directory contents. Jump back to GitHub directly.  -->
  For license texts have a look at [third-party-licenses](https://github.com/CoreMedia/feedback-hub-adapter-searchmetrics/tree/cmcc-10-2007/docs/third-party-licenses).



# Adapter Configuration

The configuration of the adpater consists of a settings document that can be
put in a global or site-specific folder.

- Global: _/Settings/Options/Settings/Feedback Hub/_
- Site specific: _&lt;SITE&gt;/Options/Settings/Feedback Hub/_


## General Settings

The adapter settings can be configured on a global or site specific level. The following
XML shows an example configuration. If you don't know how to create your Searchmetrics API key,
please visit https://developers.searchmetrics.com/ for more detailed instructions.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<CMSettings folder="/Settings/Options/Settings/Feedback Hub" name="Searchmetrics Adapter (Global)">
  <locale></locale>
  <master>
  </master>
  <settings>
    <Struct xmlns="http://www.coremedia.com/2008/struct">
      <StringProperty Name="factoryId">searchmetrics</StringProperty>
      <StringProperty Name="groupId">searchmetrics</StringProperty>
      <StringProperty Name="contentType">CMArticle</StringProperty>
      <StringProperty Name="reloadMode">auto</StringProperty>
      <BooleanProperty Name="enabled">true</BooleanProperty>
      <StructProperty Name="settings">
        <Struct>
          <StringProperty Name="apiKey">YOUR_API_KEY</StringProperty>
          <StringProperty Name="sharedSecret">YOUR_SHARED_API_KEY</StringProperty>
          <StringProperty Name="sourceProperties">detailText</StringProperty>
          <IntProperty Name="projectId">YOUR_PROJECT_ID</IntProperty>
          <BooleanProperty Name="includeKeywords">true</BooleanProperty>
          <BooleanProperty Name="includeTaxonomies">true</BooleanProperty>
        </Struct>
      </StructProperty>
    </Struct>
  </settings>
  <identifier></identifier>
</CMSettings>

```


Every _settings_ sub-struct contains the following properties:

| Property          | Description   |
| ----------------- | ------------- |
| apiKey            | The API key from the Searchmetrics developer portal |
| sharedSecret      | The shared secret from the Searchmetrics developer portal |
| propertyName      | The richtext property name that should be used for analysis from the previously defined content type |
| projectId         | The Searchmetrics project id (readable from the URL string in the Searchmetrics Content Editor) |
| includeKeywords   | Set to true to include the plain text keywords in the content analysis |
| includeKeywords   | Set to true to include taxonomy keywords in the content analysis |


