package com.app.habittracker.presentation.quests;

import com.app.habittracker.domain.engines.SmartQuestEngine;
import com.app.habittracker.domain.repository.UserRepository;
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
public final class DailyQuestsViewModel_MembersInjector implements MembersInjector<DailyQuestsViewModel> {
  private final Provider<SmartQuestEngine> smartQuestEngineProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public DailyQuestsViewModel_MembersInjector(Provider<SmartQuestEngine> smartQuestEngineProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.smartQuestEngineProvider = smartQuestEngineProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  public static MembersInjector<DailyQuestsViewModel> create(
      Provider<SmartQuestEngine> smartQuestEngineProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new DailyQuestsViewModel_MembersInjector(smartQuestEngineProvider, userRepositoryProvider);
  }

  @Override
  public void injectMembers(DailyQuestsViewModel instance) {
    injectSmartQuestEngine(instance, smartQuestEngineProvider.get());
    injectUserRepository(instance, userRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.app.habittracker.presentation.quests.DailyQuestsViewModel.smartQuestEngine")
  public static void injectSmartQuestEngine(DailyQuestsViewModel instance,
      SmartQuestEngine smartQuestEngine) {
    instance.smartQuestEngine = smartQuestEngine;
  }

  @InjectedFieldSignature("com.app.habittracker.presentation.quests.DailyQuestsViewModel.userRepository")
  public static void injectUserRepository(DailyQuestsViewModel instance,
      UserRepository userRepository) {
    instance.userRepository = userRepository;
  }
}
