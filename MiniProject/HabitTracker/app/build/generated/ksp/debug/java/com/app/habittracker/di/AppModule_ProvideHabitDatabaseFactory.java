package com.app.habittracker.di;

import android.app.Application;
import com.app.habittracker.data.local.HabitDatabase;
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
public final class AppModule_ProvideHabitDatabaseFactory implements Factory<HabitDatabase> {
  private final Provider<Application> appProvider;

  public AppModule_ProvideHabitDatabaseFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public HabitDatabase get() {
    return provideHabitDatabase(appProvider.get());
  }

  public static AppModule_ProvideHabitDatabaseFactory create(Provider<Application> appProvider) {
    return new AppModule_ProvideHabitDatabaseFactory(appProvider);
  }

  public static HabitDatabase provideHabitDatabase(Application app) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideHabitDatabase(app));
  }
}
