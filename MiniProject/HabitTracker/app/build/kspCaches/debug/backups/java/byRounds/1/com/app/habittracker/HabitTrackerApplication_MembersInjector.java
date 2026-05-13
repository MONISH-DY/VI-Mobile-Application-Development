package com.app.habittracker;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class HabitTrackerApplication_MembersInjector implements MembersInjector<HabitTrackerApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public HabitTrackerApplication_MembersInjector(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<HabitTrackerApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new HabitTrackerApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(HabitTrackerApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.app.habittracker.HabitTrackerApplication.workerFactory")
  public static void injectWorkerFactory(HabitTrackerApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
