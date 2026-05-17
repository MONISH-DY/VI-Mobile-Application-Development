package com.app.habittracker.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.app.habittracker.domain.engines.GamificationEngine;
import com.app.habittracker.domain.repository.HabitRepository;
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
public final class HabitStatusWorker_Factory {
  private final Provider<HabitRepository> habitRepositoryProvider;

  private final Provider<GamificationEngine> gamificationEngineProvider;

  public HabitStatusWorker_Factory(Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
    this.gamificationEngineProvider = gamificationEngineProvider;
  }

  public HabitStatusWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, habitRepositoryProvider.get(), gamificationEngineProvider.get());
  }

  public static HabitStatusWorker_Factory create(Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider) {
    return new HabitStatusWorker_Factory(habitRepositoryProvider, gamificationEngineProvider);
  }

  public static HabitStatusWorker newInstance(Context context, WorkerParameters workerParams,
      HabitRepository habitRepository, GamificationEngine gamificationEngine) {
    return new HabitStatusWorker(context, workerParams, habitRepository, gamificationEngine);
  }
}
