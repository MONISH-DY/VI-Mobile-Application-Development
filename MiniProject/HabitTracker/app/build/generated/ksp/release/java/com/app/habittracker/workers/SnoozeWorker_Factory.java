package com.app.habittracker.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
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
public final class SnoozeWorker_Factory {
  private final Provider<HabitRepository> habitRepositoryProvider;

  public SnoozeWorker_Factory(Provider<HabitRepository> habitRepositoryProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
  }

  public SnoozeWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, habitRepositoryProvider.get());
  }

  public static SnoozeWorker_Factory create(Provider<HabitRepository> habitRepositoryProvider) {
    return new SnoozeWorker_Factory(habitRepositoryProvider);
  }

  public static SnoozeWorker newInstance(Context context, WorkerParameters workerParams,
      HabitRepository habitRepository) {
    return new SnoozeWorker(context, workerParams, habitRepository);
  }
}
