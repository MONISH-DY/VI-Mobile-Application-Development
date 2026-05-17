package com.app.habittracker.di;

import android.app.Application;
import com.app.habittracker.data.local.PreferencesManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppModule_ProvidePreferencesManagerFactory implements Factory<PreferencesManager> {
  private final Provider<Application> appProvider;

  public AppModule_ProvidePreferencesManagerFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public PreferencesManager get() {
    return providePreferencesManager(appProvider.get());
  }

  public static AppModule_ProvidePreferencesManagerFactory create(
      Provider<Application> appProvider) {
    return new AppModule_ProvidePreferencesManagerFactory(appProvider);
  }

  public static PreferencesManager providePreferencesManager(Application app) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePreferencesManager(app));
  }
}
