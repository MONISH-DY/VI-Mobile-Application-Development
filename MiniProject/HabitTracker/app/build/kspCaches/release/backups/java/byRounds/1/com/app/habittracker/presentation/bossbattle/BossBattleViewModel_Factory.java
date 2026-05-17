package com.app.habittracker.presentation.bossbattle;

import com.app.habittracker.domain.engines.GamificationEngine;
import com.app.habittracker.domain.engines.SmartQuestEngine;
import com.app.habittracker.domain.repository.BossBattleRepository;
import com.app.habittracker.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class BossBattleViewModel_Factory implements Factory<BossBattleViewModel> {
  private final Provider<BossBattleRepository> bossBattleRepositoryProvider;

  private final Provider<GamificationEngine> gamificationEngineProvider;

  private final Provider<SmartQuestEngine> smartQuestEngineProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public BossBattleViewModel_Factory(Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<SmartQuestEngine> smartQuestEngineProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.bossBattleRepositoryProvider = bossBattleRepositoryProvider;
    this.gamificationEngineProvider = gamificationEngineProvider;
    this.smartQuestEngineProvider = smartQuestEngineProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public BossBattleViewModel get() {
    return newInstance(bossBattleRepositoryProvider.get(), gamificationEngineProvider.get(), smartQuestEngineProvider.get(), userRepositoryProvider.get());
  }

  public static BossBattleViewModel_Factory create(
      Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<SmartQuestEngine> smartQuestEngineProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new BossBattleViewModel_Factory(bossBattleRepositoryProvider, gamificationEngineProvider, smartQuestEngineProvider, userRepositoryProvider);
  }

  public static BossBattleViewModel newInstance(BossBattleRepository bossBattleRepository,
      GamificationEngine gamificationEngine, SmartQuestEngine smartQuestEngine,
      UserRepository userRepository) {
    return new BossBattleViewModel(bossBattleRepository, gamificationEngine, smartQuestEngine, userRepository);
  }
}
