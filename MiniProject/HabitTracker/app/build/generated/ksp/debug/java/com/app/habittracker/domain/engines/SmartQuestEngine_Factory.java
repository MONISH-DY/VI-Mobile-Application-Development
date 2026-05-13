package com.app.habittracker.domain.engines;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SmartQuestEngine_Factory implements Factory<SmartQuestEngine> {
  @Override
  public SmartQuestEngine get() {
    return newInstance();
  }

  public static SmartQuestEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SmartQuestEngine newInstance() {
    return new SmartQuestEngine();
  }

  private static final class InstanceHolder {
    private static final SmartQuestEngine_Factory INSTANCE = new SmartQuestEngine_Factory();
  }
}
