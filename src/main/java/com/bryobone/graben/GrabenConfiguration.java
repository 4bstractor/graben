package com.bryobone.graben;

import com.bryobone.graben.core.Template;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class GrabenConfiguration extends Configuration {
  @NotEmpty
  private String template;

  @NotEmpty
  private String defaultName = "Stranger";

  @NotEmpty
  private String cacheBuilderArgs;

  @Valid
  @NotNull
  private DataSourceFactory database = new DataSourceFactory();

  @JsonProperty
  public String getTemplate() {
      return template;
  }

  @JsonProperty
  public void setTemplate(String template) {
      this.template = template;
  }

  public Template buildTemplate() {
    return new Template(template, defaultName);
  }

  @JsonProperty
  public String getDefaultName() {
      return defaultName;
  }

  @JsonProperty
  public void setDefaultName(String defaultName) { this.defaultName = defaultName; }

  @JsonProperty("cacheBuilderArgs")
  public String getCacheBuilderArgs() { return cacheBuilderArgs; }

  @JsonProperty("cacheBuilderArgs")
  public void setCacheBuilderArgs(String cacheBuilderArgs) { this.cacheBuilderArgs = cacheBuilderArgs; }

  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
      return database;
  }

  @JsonProperty("database")
  public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
      this.database = dataSourceFactory;
  }
}
