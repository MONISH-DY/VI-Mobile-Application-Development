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
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<HabitRepository> habitRepositoryProvider;

  public BootReceiver_MembersInjector(Provider<HabitRepository> habitRepositoryProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
  }

  public static MembersInjector<BootReceiver> create(
      Provider<HabitRepository> habitRepositoryProvider) {
    return new BootReceiver_MembersInjector(habitRepositoryProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectHabitRepository(instance, habitRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.app.habittracker.workers.BootReceiver.habitRepository")
  public static void injectHabitRepository(BootReceiver instance, HabitRepository habitRepository) {
    instance.habitRepository = habitRepository;
  }
}
