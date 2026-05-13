package com.app.habittracker.di;

import com.app.habittracker.data.local.HabitDatabase;
import com.app.habittracker.data.local.dao.XPHistoryDao;
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
public final class AppModule_ProvideXPHistoryDaoFactory implements Factory<XPHistoryDao> {
  private final Provider<HabitDatabase> dbProvider;

  public AppModule_ProvideXPHistoryDaoFactory(Provider<HabitDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public XPHistoryDao get() {
    return provideXPHistoryDao(dbProvider.get());
  }

  public static AppModule_ProvideXPHistoryDaoFactory create(Provider<HabitDatabase> dbProvider) {
    return new AppModule_ProvideXPHistoryDaoFactory(dbProvider);
  }

  public static XPHistoryDao provideXPHistoryDao(HabitDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideXPHistoryDao(db));
  }
}
