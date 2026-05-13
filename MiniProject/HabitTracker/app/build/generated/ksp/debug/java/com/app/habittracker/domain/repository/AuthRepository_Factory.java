package com.app.habittracker.domain.repository;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.UserDao;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<UserDao> userDaoProvider;

  public AuthRepository_Factory(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<UserDao> userDaoProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.userDaoProvider = userDaoProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(preferencesManagerProvider.get(), userDaoProvider.get());
  }

  public static AuthRepository_Factory create(
      Provider<PreferencesManager> preferencesManagerProvider, Provider<UserDao> userDaoProvider) {
    return new AuthRepository_Factory(preferencesManagerProvider, userDaoProvider);
  }

  public static AuthRepository newInstance(PreferencesManager preferencesManager, UserDao userDao) {
    return new AuthRepository(preferencesManager, userDao);
  }
}
