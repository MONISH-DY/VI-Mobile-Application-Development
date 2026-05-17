package com.app.habittracker.workers;

import com.app.habittracker.domain.engines.GamificationEngine;
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
public final class HabitActionReceiver_MembersInjector implements MembersInjector<HabitActionReceiver> {
  private final Provider<HabitRepository> habitRepositoryProvider;

  private final Provider<GamificationEngine> gamificationEngineProvider;

  public HabitActionReceiver_MembersInjector(Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
    this.gamificationEngineProvider = gamificationEngineProvider;
  }

  public static MembersInjector<HabitActionReceiver> create(
      Provider<HabitRepository> habitRepositoryProvider,
      Provider<GamificationEngine> gamificationEngineProvider) {
    return new HabitActionReceiver_MembersInjector(habitRepositoryProvider, gamificationEngineProvider);
  }

  @Override
  public void injectMembers(HabitActionReceiver instance) {
    injectHabitRepository(instance, habitRepositoryProvider.get());
    injectGamificationEngine(instance, gamificationEngineProvider.get());
  }

  @InjectedFieldSignature("com.app.habittracker.workers.HabitActionReceiver.habitRepository")
  public static void injectHabitRepository(HabitActionReceiver instance,
      HabitRepository habitRepository) {
    instance.habitRepository = habitRepository;
  }

  @InjectedFieldSignature("com.app.habittracker.workers.HabitActionReceiver.gamificationEngine")
  public static void injectGamificationEngine(HabitActionReceiver instance,
      GamificationEngine gamificationEngine) {
    instance.gamificationEngine = gamificationEngine;
  }
}
