package com.app.habittracker.di;

import com.app.habittracker.data.local.HabitDatabase;
import com.app.habittracker.data.local.dao.QuestDao;
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
public final class AppModule_ProvideQuestDaoFactory implements Factory<QuestDao> {
  private final Provider<HabitDatabase> dbProvider;

  public AppModule_ProvideQuestDaoFactory(Provider<HabitDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public QuestDao get() {
    return provideQuestDao(dbProvider.get());
  }

  public static AppModule_ProvideQuestDaoFactory create(Provider<HabitDatabase> dbProvider) {
    return new AppModule_ProvideQuestDaoFactory(dbProvider);
  }

  public static QuestDao provideQuestDao(HabitDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQuestDao(db));
  }
}
