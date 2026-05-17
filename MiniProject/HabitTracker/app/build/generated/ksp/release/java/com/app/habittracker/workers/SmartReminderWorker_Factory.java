package com.app.habittracker.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
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
public final class SmartReminderWorker_Factory {
  private final Provider<HabitRepository> habitRepositoryProvider;

  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public SmartReminderWorker_Factory(Provider<HabitRepository> habitRepositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
    this.questRepositoryProvider = questRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  public SmartReminderWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, habitRepositoryProvider.get(), questRepositoryProvider.get(), userRepositoryProvider.get());
  }

  public static SmartReminderWorker_Factory create(
      Provider<HabitRepository> habitRepositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new SmartReminderWorker_Factory(habitRepositoryProvider, questRepositoryProvider, userRepositoryProvider);
  }

  public static SmartReminderWorker newInstance(Context context, WorkerParameters workerParams,
      HabitRepository habitRepository, QuestRepository questRepository,
      UserRepository userRepository) {
    return new SmartReminderWorker(context, workerParams, habitRepository, questRepository, userRepository);
  }
}
