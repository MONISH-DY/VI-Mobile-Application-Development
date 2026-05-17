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
public final class HabitPulseApplication_MembersInjector implements MembersInjector<HabitPulseApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public HabitPulseApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<HabitPulseApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new HabitPulseApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(HabitPulseApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.app.habittracker.HabitPulseApplication.workerFactory")
  public static void injectWorkerFactory(HabitPulseApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
