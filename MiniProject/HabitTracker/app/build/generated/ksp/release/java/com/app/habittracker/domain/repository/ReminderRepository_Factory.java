package com.app.habittracker.domain.repository;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.ReminderDao;
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
public final class ReminderRepository_Factory implements Factory<ReminderRepository> {
  private final Provider<ReminderDao> reminderDaoProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public ReminderRepository_Factory(Provider<ReminderDao> reminderDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.reminderDaoProvider = reminderDaoProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public ReminderRepository get() {
    return newInstance(reminderDaoProvider.get(), preferencesManagerProvider.get());
  }

  public static ReminderRepository_Factory create(Provider<ReminderDao> reminderDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new ReminderRepository_Factory(reminderDaoProvider, preferencesManagerProvider);
  }

  public static ReminderRepository newInstance(ReminderDao reminderDao,
      PreferencesManager preferencesManager) {
    return new ReminderRepository(reminderDao, preferencesManager);
  }
}
