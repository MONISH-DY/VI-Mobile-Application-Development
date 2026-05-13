package com.app.habittracker.domain.repository;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.BossBattleDao;
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
public final class BossBattleRepository_Factory implements Factory<BossBattleRepository> {
  private final Provider<BossBattleDao> bossBattleDaoProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public BossBattleRepository_Factory(Provider<BossBattleDao> bossBattleDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.bossBattleDaoProvider = bossBattleDaoProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public BossBattleRepository get() {
    return newInstance(bossBattleDaoProvider.get(), preferencesManagerProvider.get());
  }

  public static BossBattleRepository_Factory create(Provider<BossBattleDao> bossBattleDaoProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new BossBattleRepository_Factory(bossBattleDaoProvider, preferencesManagerProvider);
  }

  public static BossBattleRepository newInstance(BossBattleDao bossBattleDao,
      PreferencesManager preferencesManager) {
    return new BossBattleRepository(bossBattleDao, preferencesManager);
  }
}
