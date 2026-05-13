package com.app.habittracker.domain.repository;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.HabitDao;
import com.app.habittracker.data.local.dao.HabitHistoryDao;
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
public final class HabitRepository_Factory implements Factory<HabitRepository> {
  private final Provider<HabitDao> habitDaoProvider;

  private final Provider<HabitHistoryDao> habitHistoryDaoProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public HabitRepository_Factory(Provider<HabitDao> habitDaoProvider,
      Provider<HabitHistoryDao> habitHistoryDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.habitDaoProvider = habitDaoProvider;
    this.habitHistoryDaoProvider = habitHistoryDaoProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public HabitRepository get() {
    return newInstance(habitDaoProvider.get(), habitHistoryDaoProvider.get(), preferencesManagerProvider.get());
  }

  public static HabitRepository_Factory create(Provider<HabitDao> habitDaoProvider,
      Provider<HabitHistoryDao> habitHistoryDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new HabitRepository_Factory(habitDaoProvider, habitHistoryDaoProvider, preferencesManagerProvider);
  }

  public static HabitRepository newInstance(HabitDao habitDao, HabitHistoryDao habitHistoryDao,
      PreferencesManager preferencesManager) {
    return new HabitRepository(habitDao, habitHistoryDao, preferencesManager);
  }
}
