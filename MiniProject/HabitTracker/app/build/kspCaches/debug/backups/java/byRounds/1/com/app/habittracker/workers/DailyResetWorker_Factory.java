package com.app.habittracker.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.app.habittracker.domain.engines.GamificationEngine;
import com.app.habittracker.domain.engines.SmartQuestEngine;
import com.app.habittracker.domain.repository.BossBattleRepository;
import com.app.habittracker.domain.repository.HabitRepository;
import com.app.habittracker.domain.repository.QuestRepository;
import com.app.habittracker.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
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
public final class DailyResetWorker_Factory {
  private final Provider<HabitRepository> habitRepositoryProvider;

  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<BossBattleRepository> bossBattleRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<GamificationEngine> gamificationEngineProvider;

  private final Provider<SmartQuestEngine> smartQuestEngineProvider;

  public DailyResetWorker_Factory(Provider<HabitRepository> habitRepositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<SmartQuestEngine> smartQuestEngineProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
    this.questRepositoryProvider = questRepositoryProvider;
    this.bossBattleRepositoryProvider = bossBattleRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.gamificationEngineProvider = gamificationEngineProvider;
    this.smartQuestEngineProvider = smartQuestEngineProvider;
  }

  public DailyResetWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, habitRepositoryProvider.get(), questRepositoryProvider.get(), bossBattleRepositoryProvider.get(), userRepositoryProvider.get(), gamificationEngineProvider.get(), smartQuestEngineProvider.get());
  }

  public static DailyResetWorker_Factory create(Provider<HabitRepository> habitRepositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<BossBattleRepository> bossBattleRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider,
      Provider<SmartQuestEngine> smartQuestEngineProvider) {
    return new DailyResetWorker_Factory(habitRepositoryProvider, questRepositoryProvider, bossBattleRepositoryProvider, userRepositoryProvider, gamificationEngineProvider, smartQuestEngineProvider);
  }

  public static DailyResetWorker newInstance(Context context, WorkerParameters workerParams,
      HabitRepository habitRepository, QuestRepository questRepository,
      BossBattleRepository bossBattleRepository, UserRepository userRepository,
      GamificationEngine gamificationEngine, SmartQuestEngine smartQuestEngine) {
    return new DailyResetWorker(context, workerParams, habitRepository, questRepository, bossBattleRepository, userRepository, gamificationEngine, smartQuestEngine);
  }
}
