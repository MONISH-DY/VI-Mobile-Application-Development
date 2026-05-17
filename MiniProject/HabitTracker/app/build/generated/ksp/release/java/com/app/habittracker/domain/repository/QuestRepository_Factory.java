package com.app.habittracker.domain.repository;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.QuestDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class QuestRepository_Factory implements Factory<QuestRepository> {
  private final Provider<QuestDao> questDaoProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public QuestRepository_Factory(Provider<QuestDao> questDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.questDaoProvider = questDaoProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public QuestRepository get() {
    return newInstance(questDaoProvider.get(), preferencesManagerProvider.get());
  }

  public static QuestRepository_Factory create(Provider<QuestDao> questDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new QuestRepository_Factory(questDaoProvider, preferencesManagerProvider);
  }

  public static QuestRepository newInstance(QuestDao questDao,
      PreferencesManager preferencesManager) {
    return new QuestRepository(questDao, preferencesManager);
  }
}
