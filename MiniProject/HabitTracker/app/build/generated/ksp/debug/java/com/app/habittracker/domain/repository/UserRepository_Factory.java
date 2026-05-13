package com.app.habittracker.domain.repository;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.UserDao;
import com.app.habittracker.data.local.dao.XPHistoryDao;
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
public final class UserRepository_Factory implements Factory<UserRepository> {
  private final Provider<UserDao> userDaoProvider;

  private final Provider<XPHistoryDao> xpHistoryDaoProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public UserRepository_Factory(Provider<UserDao> userDaoProvider,
      Provider<XPHistoryDao> xpHistoryDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.userDaoProvider = userDaoProvider;
    this.xpHistoryDaoProvider = xpHistoryDaoProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public UserRepository get() {
    return newInstance(userDaoProvider.get(), xpHistoryDaoProvider.get(), preferencesManagerProvider.get());
  }

  public static UserRepository_Factory create(Provider<UserDao> userDaoProvider,
      Provider<XPHistoryDao> xpHistoryDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new UserRepository_Factory(userDaoProvider, xpHistoryDaoProvider, preferencesManagerProvider);
  }

  public static UserRepository newInstance(UserDao userDao, XPHistoryDao xpHistoryDao,
      PreferencesManager preferencesManager) {
    return new UserRepository(userDao, xpHistoryDao, preferencesManager);
  }
}
