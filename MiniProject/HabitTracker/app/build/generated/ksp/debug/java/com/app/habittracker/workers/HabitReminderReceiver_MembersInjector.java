package com.app.habittracker.workers;

import com.app.habittracker.domain.repository.HabitRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class HabitReminderReceiver_MembersInjector implements MembersInjector<HabitReminderReceiver> {
  private final Provider<HabitRepository> habitRepositoryProvider;

  public HabitReminderReceiver_MembersInjector(Provider<HabitRepository> habitRepositoryProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
  }

  public static MembersInjector<HabitReminderReceiver> create(
      Provider<HabitRepository> habitRepositoryProvider) {
    return new HabitReminderReceiver_MembersInjector(habitRepositoryProvider);
  }

  @Override
  public void injectMembers(HabitReminderReceiver instance) {
    injectHabitRepository(instance, habitRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.app.habittracker.workers.HabitReminderReceiver.habitRepository")
  public static void injectHabitRepository(HabitReminderReceiver instance,
      HabitRepository habitRepository) {
    instance.habitRepository = habitRepository;
  }
}
