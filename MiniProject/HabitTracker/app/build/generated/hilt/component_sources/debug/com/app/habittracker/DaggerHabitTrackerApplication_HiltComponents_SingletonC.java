package com.app.habittracker;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.app.habittracker.data.local.HabitDatabase;
import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.data.local.dao.AchievementDao;
import com.app.habittracker.data.local.dao.BossBattleDao;
import com.app.habittracker.data.local.dao.HabitDao;
import com.app.habittracker.data.local.dao.HabitHistoryDao;
import com.app.habittracker.data.local.dao.QuestDao;
import com.app.habittracker.data.local.dao.UserDao;
import com.app.habittracker.data.local.dao.XPHistoryDao;
import com.app.habittracker.di.AppModule_ProvideAchievementDaoFactory;
import com.app.habittracker.di.AppModule_ProvideApplicationScopeFactory;
import com.app.habittracker.di.AppModule_ProvideBossBattleDaoFactory;
import com.app.habittracker.di.AppModule_ProvideHabitDaoFactory;
import com.app.habittracker.di.AppModule_ProvideHabitDatabaseFactory;
import com.app.habittracker.di.AppModule_ProvideHabitHistoryDaoFactory;
import com.app.habittracker.di.AppModule_ProvidePreferencesManagerFactory;
import com.app.habittracker.di.AppModule_ProvideQuestDaoFactory;
import com.app.habittracker.di.AppModule_ProvideUserDaoFactory;
import com.app.habittracker.di.AppModule_ProvideXPHistoryDaoFactory;
import com.app.habittracker.domain.engines.GamificationEngine;
import com.app.habittracker.domain.engines.SmartQuestEngine;
import com.app.habittracker.domain.engines.TimerManager;
import com.app.habittracker.domain.repository.AchievementRepository;
import com.app.habittracker.domain.repository.AuthRepository;
import com.app.habittracker.domain.repository.BossBattleRepository;
import com.app.habittracker.domain.repository.HabitRepository;
import com.app.habittracker.domain.repository.QuestRepository;
import com.app.habittracker.domain.repository.UserRepository;
import com.app.habittracker.presentation.achievements.AchievementsViewModel;
import com.app.habittracker.presentation.achievements.AchievementsViewModel_HiltModules;
import com.app.habittracker.presentation.analytics.AnalyticsViewModel;
import com.app.habittracker.presentation.analytics.AnalyticsViewModel_HiltModules;
import com.app.habittracker.presentation.auth.AuthViewModel;
import com.app.habittracker.presentation.auth.AuthViewModel_HiltModules;
import com.app.habittracker.presentation.bossbattle.BossBattleViewModel;
import com.app.habittracker.presentation.bossbattle.BossBattleViewModel_HiltModules;
import com.app.habittracker.presentation.calendar.CalendarViewModel;
import com.app.habittracker.presentation.calendar.CalendarViewModel_HiltModules;
import com.app.habittracker.presentation.common.GlobalTimerViewModel;
import com.app.habittracker.presentation.common.GlobalTimerViewModel_HiltModules;
import com.app.habittracker.presentation.dashboard.DashboardViewModel;
import com.app.habittracker.presentation.dashboard.DashboardViewModel_HiltModules;
import com.app.habittracker.presentation.habits.HabitCreationViewModel;
import com.app.habittracker.presentation.habits.HabitCreationViewModel_HiltModules;
import com.app.habittracker.presentation.landing.LandingViewModel;
import com.app.habittracker.presentation.landing.LandingViewModel_HiltModules;
import com.app.habittracker.presentation.mastery.MasteryViewModel;
import com.app.habittracker.presentation.mastery.MasteryViewModel_HiltModules;
import com.app.habittracker.presentation.onboarding.OnboardingViewModel;
import com.app.habittracker.presentation.onboarding.OnboardingViewModel_HiltModules;
import com.app.habittracker.presentation.profile.ProfileViewModel;
import com.app.habittracker.presentation.profile.ProfileViewModel_HiltModules;
import com.app.habittracker.presentation.quests.DailyQuestsViewModel;
import com.app.habittracker.presentation.quests.DailyQuestsViewModel_Factory;
import com.app.habittracker.presentation.quests.DailyQuestsViewModel_HiltModules;
import com.app.habittracker.presentation.quests.DailyQuestsViewModel_MembersInjector;
import com.app.habittracker.presentation.splash.SplashViewModel;
import com.app.habittracker.presentation.splash.SplashViewModel_HiltModules;
import com.app.habittracker.workers.BootReceiver;
import com.app.habittracker.workers.BootReceiver_MembersInjector;
import com.app.habittracker.workers.DailyResetWorker;
import com.app.habittracker.workers.DailyResetWorker_AssistedFactory;
import com.app.habittracker.workers.HabitActionReceiver;
import com.app.habittracker.workers.HabitActionReceiver_MembersInjector;
import com.app.habittracker.workers.HabitReminderReceiver;
import com.app.habittracker.workers.HabitReminderReceiver_MembersInjector;
import com.app.habittracker.workers.HabitStatusWorker;
import com.app.habittracker.workers.HabitStatusWorker_AssistedFactory;
import com.app.habittracker.workers.SmartReminderWorker;
import com.app.habittracker.workers.SmartReminderWorker_AssistedFactory;
import com.app.habittracker.workers.SnoozeWorker;
import com.app.habittracker.workers.SnoozeWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;

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
public final class DaggerHabitTrackerApplication_HiltComponents_SingletonC {
  private DaggerHabitTrackerApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public HabitTrackerApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements HabitTrackerApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public HabitTrackerApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements HabitTrackerApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public HabitTrackerApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements HabitTrackerApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public HabitTrackerApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements HabitTrackerApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public HabitTrackerApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements HabitTrackerApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public HabitTrackerApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements HabitTrackerApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public HabitTrackerApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements HabitTrackerApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public HabitTrackerApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends HabitTrackerApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends HabitTrackerApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends HabitTrackerApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends HabitTrackerApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(14).put(LazyClassKeyProvider.com_app_habittracker_presentation_achievements_AchievementsViewModel, AchievementsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_analytics_AnalyticsViewModel, AnalyticsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_auth_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_bossbattle_BossBattleViewModel, BossBattleViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_calendar_CalendarViewModel, CalendarViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_quests_DailyQuestsViewModel, DailyQuestsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_common_GlobalTimerViewModel, GlobalTimerViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_habits_HabitCreationViewModel, HabitCreationViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_landing_LandingViewModel, LandingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_mastery_MasteryViewModel, MasteryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_onboarding_OnboardingViewModel, OnboardingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_profile_ProfileViewModel, ProfileViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_habittracker_presentation_splash_SplashViewModel, SplashViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectPreferencesManager(instance, singletonCImpl.providePreferencesManagerProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_app_habittracker_presentation_quests_DailyQuestsViewModel = "com.app.habittracker.presentation.quests.DailyQuestsViewModel";

      static String com_app_habittracker_presentation_profile_ProfileViewModel = "com.app.habittracker.presentation.profile.ProfileViewModel";

      static String com_app_habittracker_presentation_calendar_CalendarViewModel = "com.app.habittracker.presentation.calendar.CalendarViewModel";

      static String com_app_habittracker_presentation_habits_HabitCreationViewModel = "com.app.habittracker.presentation.habits.HabitCreationViewModel";

      static String com_app_habittracker_presentation_auth_AuthViewModel = "com.app.habittracker.presentation.auth.AuthViewModel";

      static String com_app_habittracker_presentation_common_GlobalTimerViewModel = "com.app.habittracker.presentation.common.GlobalTimerViewModel";

      static String com_app_habittracker_presentation_mastery_MasteryViewModel = "com.app.habittracker.presentation.mastery.MasteryViewModel";

      static String com_app_habittracker_presentation_onboarding_OnboardingViewModel = "com.app.habittracker.presentation.onboarding.OnboardingViewModel";

      static String com_app_habittracker_presentation_analytics_AnalyticsViewModel = "com.app.habittracker.presentation.analytics.AnalyticsViewModel";

      static String com_app_habittracker_presentation_bossbattle_BossBattleViewModel = "com.app.habittracker.presentation.bossbattle.BossBattleViewModel";

      static String com_app_habittracker_presentation_dashboard_DashboardViewModel = "com.app.habittracker.presentation.dashboard.DashboardViewModel";

      static String com_app_habittracker_presentation_achievements_AchievementsViewModel = "com.app.habittracker.presentation.achievements.AchievementsViewModel";

      static String com_app_habittracker_presentation_splash_SplashViewModel = "com.app.habittracker.presentation.splash.SplashViewModel";

      static String com_app_habittracker_presentation_landing_LandingViewModel = "com.app.habittracker.presentation.landing.LandingViewModel";

      @KeepFieldType
      DailyQuestsViewModel com_app_habittracker_presentation_quests_DailyQuestsViewModel2;

      @KeepFieldType
      ProfileViewModel com_app_habittracker_presentation_profile_ProfileViewModel2;

      @KeepFieldType
      CalendarViewModel com_app_habittracker_presentation_calendar_CalendarViewModel2;

      @KeepFieldType
      HabitCreationViewModel com_app_habittracker_presentation_habits_HabitCreationViewModel2;

      @KeepFieldType
      AuthViewModel com_app_habittracker_presentation_auth_AuthViewModel2;

      @KeepFieldType
      GlobalTimerViewModel com_app_habittracker_presentation_common_GlobalTimerViewModel2;

      @KeepFieldType
      MasteryViewModel com_app_habittracker_presentation_mastery_MasteryViewModel2;

      @KeepFieldType
      OnboardingViewModel com_app_habittracker_presentation_onboarding_OnboardingViewModel2;

      @KeepFieldType
      AnalyticsViewModel com_app_habittracker_presentation_analytics_AnalyticsViewModel2;

      @KeepFieldType
      BossBattleViewModel com_app_habittracker_presentation_bossbattle_BossBattleViewModel2;

      @KeepFieldType
      DashboardViewModel com_app_habittracker_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      AchievementsViewModel com_app_habittracker_presentation_achievements_AchievementsViewModel2;

      @KeepFieldType
      SplashViewModel com_app_habittracker_presentation_splash_SplashViewModel2;

      @KeepFieldType
      LandingViewModel com_app_habittracker_presentation_landing_LandingViewModel2;
    }
  }

  private static final class ViewModelCImpl extends HabitTrackerApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AchievementsViewModel> achievementsViewModelProvider;

    private Provider<AnalyticsViewModel> analyticsViewModelProvider;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<BossBattleViewModel> bossBattleViewModelProvider;

    private Provider<CalendarViewModel> calendarViewModelProvider;

    private Provider<DailyQuestsViewModel> dailyQuestsViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<GlobalTimerViewModel> globalTimerViewModelProvider;

    private Provider<HabitCreationViewModel> habitCreationViewModelProvider;

    private Provider<LandingViewModel> landingViewModelProvider;

    private Provider<MasteryViewModel> masteryViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.achievementsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.analyticsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.bossBattleViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.calendarViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.dailyQuestsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.globalTimerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.habitCreationViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.landingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.masteryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(14).put(LazyClassKeyProvider.com_app_habittracker_presentation_achievements_AchievementsViewModel, ((Provider) achievementsViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_analytics_AnalyticsViewModel, ((Provider) analyticsViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_auth_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_bossbattle_BossBattleViewModel, ((Provider) bossBattleViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_calendar_CalendarViewModel, ((Provider) calendarViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_quests_DailyQuestsViewModel, ((Provider) dailyQuestsViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_common_GlobalTimerViewModel, ((Provider) globalTimerViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_habits_HabitCreationViewModel, ((Provider) habitCreationViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_landing_LandingViewModel, ((Provider) landingViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_mastery_MasteryViewModel, ((Provider) masteryViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_onboarding_OnboardingViewModel, ((Provider) onboardingViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_profile_ProfileViewModel, ((Provider) profileViewModelProvider)).put(LazyClassKeyProvider.com_app_habittracker_presentation_splash_SplashViewModel, ((Provider) splashViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    private DailyQuestsViewModel injectDailyQuestsViewModel(DailyQuestsViewModel instance) {
      DailyQuestsViewModel_MembersInjector.injectSmartQuestEngine(instance, singletonCImpl.smartQuestEngineProvider.get());
      DailyQuestsViewModel_MembersInjector.injectUserRepository(instance, singletonCImpl.userRepositoryProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_app_habittracker_presentation_achievements_AchievementsViewModel = "com.app.habittracker.presentation.achievements.AchievementsViewModel";

      static String com_app_habittracker_presentation_quests_DailyQuestsViewModel = "com.app.habittracker.presentation.quests.DailyQuestsViewModel";

      static String com_app_habittracker_presentation_landing_LandingViewModel = "com.app.habittracker.presentation.landing.LandingViewModel";

      static String com_app_habittracker_presentation_common_GlobalTimerViewModel = "com.app.habittracker.presentation.common.GlobalTimerViewModel";

      static String com_app_habittracker_presentation_onboarding_OnboardingViewModel = "com.app.habittracker.presentation.onboarding.OnboardingViewModel";

      static String com_app_habittracker_presentation_profile_ProfileViewModel = "com.app.habittracker.presentation.profile.ProfileViewModel";

      static String com_app_habittracker_presentation_mastery_MasteryViewModel = "com.app.habittracker.presentation.mastery.MasteryViewModel";

      static String com_app_habittracker_presentation_auth_AuthViewModel = "com.app.habittracker.presentation.auth.AuthViewModel";

      static String com_app_habittracker_presentation_dashboard_DashboardViewModel = "com.app.habittracker.presentation.dashboard.DashboardViewModel";

      static String com_app_habittracker_presentation_bossbattle_BossBattleViewModel = "com.app.habittracker.presentation.bossbattle.BossBattleViewModel";

      static String com_app_habittracker_presentation_habits_HabitCreationViewModel = "com.app.habittracker.presentation.habits.HabitCreationViewModel";

      static String com_app_habittracker_presentation_calendar_CalendarViewModel = "com.app.habittracker.presentation.calendar.CalendarViewModel";

      static String com_app_habittracker_presentation_analytics_AnalyticsViewModel = "com.app.habittracker.presentation.analytics.AnalyticsViewModel";

      static String com_app_habittracker_presentation_splash_SplashViewModel = "com.app.habittracker.presentation.splash.SplashViewModel";

      @KeepFieldType
      AchievementsViewModel com_app_habittracker_presentation_achievements_AchievementsViewModel2;

      @KeepFieldType
      DailyQuestsViewModel com_app_habittracker_presentation_quests_DailyQuestsViewModel2;

      @KeepFieldType
      LandingViewModel com_app_habittracker_presentation_landing_LandingViewModel2;

      @KeepFieldType
      GlobalTimerViewModel com_app_habittracker_presentation_common_GlobalTimerViewModel2;

      @KeepFieldType
      OnboardingViewModel com_app_habittracker_presentation_onboarding_OnboardingViewModel2;

      @KeepFieldType
      ProfileViewModel com_app_habittracker_presentation_profile_ProfileViewModel2;

      @KeepFieldType
      MasteryViewModel com_app_habittracker_presentation_mastery_MasteryViewModel2;

      @KeepFieldType
      AuthViewModel com_app_habittracker_presentation_auth_AuthViewModel2;

      @KeepFieldType
      DashboardViewModel com_app_habittracker_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      BossBattleViewModel com_app_habittracker_presentation_bossbattle_BossBattleViewModel2;

      @KeepFieldType
      HabitCreationViewModel com_app_habittracker_presentation_habits_HabitCreationViewModel2;

      @KeepFieldType
      CalendarViewModel com_app_habittracker_presentation_calendar_CalendarViewModel2;

      @KeepFieldType
      AnalyticsViewModel com_app_habittracker_presentation_analytics_AnalyticsViewModel2;

      @KeepFieldType
      SplashViewModel com_app_habittracker_presentation_splash_SplashViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.app.habittracker.presentation.achievements.AchievementsViewModel 
          return (T) new AchievementsViewModel(singletonCImpl.achievementRepositoryProvider.get(), singletonCImpl.gamificationEngineProvider.get());

          case 1: // com.app.habittracker.presentation.analytics.AnalyticsViewModel 
          return (T) new AnalyticsViewModel(singletonCImpl.userRepositoryProvider.get());

          case 2: // com.app.habittracker.presentation.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get());

          case 3: // com.app.habittracker.presentation.bossbattle.BossBattleViewModel 
          return (T) new BossBattleViewModel(singletonCImpl.bossBattleRepositoryProvider.get(), singletonCImpl.gamificationEngineProvider.get(), singletonCImpl.smartQuestEngineProvider.get(), singletonCImpl.userRepositoryProvider.get());

          case 4: // com.app.habittracker.presentation.calendar.CalendarViewModel 
          return (T) new CalendarViewModel(singletonCImpl.habitRepositoryProvider.get());

          case 5: // com.app.habittracker.presentation.quests.DailyQuestsViewModel 
          return (T) viewModelCImpl.injectDailyQuestsViewModel(DailyQuestsViewModel_Factory.newInstance(singletonCImpl.questRepositoryProvider.get(), singletonCImpl.habitRepositoryProvider.get(), singletonCImpl.gamificationEngineProvider.get()));

          case 6: // com.app.habittracker.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.userRepositoryProvider.get(), singletonCImpl.habitRepositoryProvider.get(), singletonCImpl.gamificationEngineProvider.get(), singletonCImpl.questRepositoryProvider.get(), singletonCImpl.bossBattleRepositoryProvider.get(), singletonCImpl.providePreferencesManagerProvider.get());

          case 7: // com.app.habittracker.presentation.common.GlobalTimerViewModel 
          return (T) new GlobalTimerViewModel(singletonCImpl.timerManagerProvider.get());

          case 8: // com.app.habittracker.presentation.habits.HabitCreationViewModel 
          return (T) new HabitCreationViewModel(singletonCImpl.habitRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.app.habittracker.presentation.landing.LandingViewModel 
          return (T) new LandingViewModel(singletonCImpl.authRepositoryProvider.get());

          case 10: // com.app.habittracker.presentation.mastery.MasteryViewModel 
          return (T) new MasteryViewModel(singletonCImpl.habitRepositoryProvider.get());

          case 11: // com.app.habittracker.presentation.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.userRepositoryProvider.get(), singletonCImpl.providePreferencesManagerProvider.get());

          case 12: // com.app.habittracker.presentation.profile.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.userRepositoryProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 13: // com.app.habittracker.presentation.splash.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.providePreferencesManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends HabitTrackerApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends HabitTrackerApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends HabitTrackerApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<HabitDatabase> provideHabitDatabaseProvider;

    private Provider<HabitDao> provideHabitDaoProvider;

    private Provider<HabitHistoryDao> provideHabitHistoryDaoProvider;

    private Provider<PreferencesManager> providePreferencesManagerProvider;

    private Provider<HabitRepository> habitRepositoryProvider;

    private Provider<QuestDao> provideQuestDaoProvider;

    private Provider<QuestRepository> questRepositoryProvider;

    private Provider<BossBattleDao> provideBossBattleDaoProvider;

    private Provider<BossBattleRepository> bossBattleRepositoryProvider;

    private Provider<UserDao> provideUserDaoProvider;

    private Provider<XPHistoryDao> provideXPHistoryDaoProvider;

    private Provider<UserRepository> userRepositoryProvider;

    private Provider<AchievementDao> provideAchievementDaoProvider;

    private Provider<AchievementRepository> achievementRepositoryProvider;

    private Provider<GamificationEngine> gamificationEngineProvider;

    private Provider<SmartQuestEngine> smartQuestEngineProvider;

    private Provider<DailyResetWorker_AssistedFactory> dailyResetWorker_AssistedFactoryProvider;

    private Provider<HabitStatusWorker_AssistedFactory> habitStatusWorker_AssistedFactoryProvider;

    private Provider<SmartReminderWorker_AssistedFactory> smartReminderWorker_AssistedFactoryProvider;

    private Provider<SnoozeWorker_AssistedFactory> snoozeWorker_AssistedFactoryProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<CoroutineScope> provideApplicationScopeProvider;

    private Provider<TimerManager> timerManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return MapBuilder.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>newMapBuilder(4).put("com.app.habittracker.workers.DailyResetWorker", ((Provider) dailyResetWorker_AssistedFactoryProvider)).put("com.app.habittracker.workers.HabitStatusWorker", ((Provider) habitStatusWorker_AssistedFactoryProvider)).put("com.app.habittracker.workers.SmartReminderWorker", ((Provider) smartReminderWorker_AssistedFactoryProvider)).put("com.app.habittracker.workers.SnoozeWorker", ((Provider) snoozeWorker_AssistedFactoryProvider)).build();
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideHabitDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<HabitDatabase>(singletonCImpl, 3));
      this.provideHabitDaoProvider = DoubleCheck.provider(new SwitchingProvider<HabitDao>(singletonCImpl, 2));
      this.provideHabitHistoryDaoProvider = DoubleCheck.provider(new SwitchingProvider<HabitHistoryDao>(singletonCImpl, 4));
      this.providePreferencesManagerProvider = DoubleCheck.provider(new SwitchingProvider<PreferencesManager>(singletonCImpl, 5));
      this.habitRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<HabitRepository>(singletonCImpl, 1));
      this.provideQuestDaoProvider = DoubleCheck.provider(new SwitchingProvider<QuestDao>(singletonCImpl, 7));
      this.questRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<QuestRepository>(singletonCImpl, 6));
      this.provideBossBattleDaoProvider = DoubleCheck.provider(new SwitchingProvider<BossBattleDao>(singletonCImpl, 9));
      this.bossBattleRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BossBattleRepository>(singletonCImpl, 8));
      this.provideUserDaoProvider = DoubleCheck.provider(new SwitchingProvider<UserDao>(singletonCImpl, 11));
      this.provideXPHistoryDaoProvider = DoubleCheck.provider(new SwitchingProvider<XPHistoryDao>(singletonCImpl, 12));
      this.userRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserRepository>(singletonCImpl, 10));
      this.provideAchievementDaoProvider = DoubleCheck.provider(new SwitchingProvider<AchievementDao>(singletonCImpl, 15));
      this.achievementRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AchievementRepository>(singletonCImpl, 14));
      this.gamificationEngineProvider = DoubleCheck.provider(new SwitchingProvider<GamificationEngine>(singletonCImpl, 13));
      this.smartQuestEngineProvider = DoubleCheck.provider(new SwitchingProvider<SmartQuestEngine>(singletonCImpl, 16));
      this.dailyResetWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<DailyResetWorker_AssistedFactory>(singletonCImpl, 0));
      this.habitStatusWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<HabitStatusWorker_AssistedFactory>(singletonCImpl, 17));
      this.smartReminderWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<SmartReminderWorker_AssistedFactory>(singletonCImpl, 18));
      this.snoozeWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<SnoozeWorker_AssistedFactory>(singletonCImpl, 19));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 20));
      this.provideApplicationScopeProvider = DoubleCheck.provider(new SwitchingProvider<CoroutineScope>(singletonCImpl, 22));
      this.timerManagerProvider = DoubleCheck.provider(new SwitchingProvider<TimerManager>(singletonCImpl, 21));
    }

    @Override
    public void injectHabitTrackerApplication(HabitTrackerApplication habitTrackerApplication) {
      injectHabitTrackerApplication2(habitTrackerApplication);
    }

    @Override
    public void injectBootReceiver(BootReceiver bootReceiver) {
      injectBootReceiver2(bootReceiver);
    }

    @Override
    public void injectHabitActionReceiver(HabitActionReceiver habitActionReceiver) {
      injectHabitActionReceiver2(habitActionReceiver);
    }

    @Override
    public void injectHabitReminderReceiver(HabitReminderReceiver habitReminderReceiver) {
      injectHabitReminderReceiver2(habitReminderReceiver);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private HabitTrackerApplication injectHabitTrackerApplication2(
        HabitTrackerApplication instance) {
      HabitTrackerApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private BootReceiver injectBootReceiver2(BootReceiver instance) {
      BootReceiver_MembersInjector.injectHabitRepository(instance, habitRepositoryProvider.get());
      return instance;
    }

    private HabitActionReceiver injectHabitActionReceiver2(HabitActionReceiver instance) {
      HabitActionReceiver_MembersInjector.injectHabitRepository(instance, habitRepositoryProvider.get());
      HabitActionReceiver_MembersInjector.injectGamificationEngine(instance, gamificationEngineProvider.get());
      return instance;
    }

    private HabitReminderReceiver injectHabitReminderReceiver2(HabitReminderReceiver instance) {
      HabitReminderReceiver_MembersInjector.injectHabitRepository(instance, habitRepositoryProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.app.habittracker.workers.DailyResetWorker_AssistedFactory 
          return (T) new DailyResetWorker_AssistedFactory() {
            @Override
            public DailyResetWorker create(Context context, WorkerParameters workerParams) {
              return new DailyResetWorker(context, workerParams, singletonCImpl.habitRepositoryProvider.get(), singletonCImpl.questRepositoryProvider.get(), singletonCImpl.bossBattleRepositoryProvider.get(), singletonCImpl.userRepositoryProvider.get(), singletonCImpl.gamificationEngineProvider.get(), singletonCImpl.smartQuestEngineProvider.get());
            }
          };

          case 1: // com.app.habittracker.domain.repository.HabitRepository 
          return (T) new HabitRepository(singletonCImpl.provideHabitDaoProvider.get(), singletonCImpl.provideHabitHistoryDaoProvider.get(), singletonCImpl.providePreferencesManagerProvider.get());

          case 2: // com.app.habittracker.data.local.dao.HabitDao 
          return (T) AppModule_ProvideHabitDaoFactory.provideHabitDao(singletonCImpl.provideHabitDatabaseProvider.get());

          case 3: // com.app.habittracker.data.local.HabitDatabase 
          return (T) AppModule_ProvideHabitDatabaseFactory.provideHabitDatabase(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 4: // com.app.habittracker.data.local.dao.HabitHistoryDao 
          return (T) AppModule_ProvideHabitHistoryDaoFactory.provideHabitHistoryDao(singletonCImpl.provideHabitDatabaseProvider.get());

          case 5: // com.app.habittracker.data.local.PreferencesManager 
          return (T) AppModule_ProvidePreferencesManagerFactory.providePreferencesManager(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 6: // com.app.habittracker.domain.repository.QuestRepository 
          return (T) new QuestRepository(singletonCImpl.provideQuestDaoProvider.get(), singletonCImpl.providePreferencesManagerProvider.get());

          case 7: // com.app.habittracker.data.local.dao.QuestDao 
          return (T) AppModule_ProvideQuestDaoFactory.provideQuestDao(singletonCImpl.provideHabitDatabaseProvider.get());

          case 8: // com.app.habittracker.domain.repository.BossBattleRepository 
          return (T) new BossBattleRepository(singletonCImpl.provideBossBattleDaoProvider.get(), singletonCImpl.providePreferencesManagerProvider.get());

          case 9: // com.app.habittracker.data.local.dao.BossBattleDao 
          return (T) AppModule_ProvideBossBattleDaoFactory.provideBossBattleDao(singletonCImpl.provideHabitDatabaseProvider.get());

          case 10: // com.app.habittracker.domain.repository.UserRepository 
          return (T) new UserRepository(singletonCImpl.provideUserDaoProvider.get(), singletonCImpl.provideXPHistoryDaoProvider.get(), singletonCImpl.providePreferencesManagerProvider.get());

          case 11: // com.app.habittracker.data.local.dao.UserDao 
          return (T) AppModule_ProvideUserDaoFactory.provideUserDao(singletonCImpl.provideHabitDatabaseProvider.get());

          case 12: // com.app.habittracker.data.local.dao.XPHistoryDao 
          return (T) AppModule_ProvideXPHistoryDaoFactory.provideXPHistoryDao(singletonCImpl.provideHabitDatabaseProvider.get());

          case 13: // com.app.habittracker.domain.engines.GamificationEngine 
          return (T) new GamificationEngine(singletonCImpl.userRepositoryProvider.get(), singletonCImpl.achievementRepositoryProvider.get(), singletonCImpl.bossBattleRepositoryProvider.get(), singletonCImpl.habitRepositoryProvider.get());

          case 14: // com.app.habittracker.domain.repository.AchievementRepository 
          return (T) new AchievementRepository(singletonCImpl.provideAchievementDaoProvider.get(), singletonCImpl.providePreferencesManagerProvider.get());

          case 15: // com.app.habittracker.data.local.dao.AchievementDao 
          return (T) AppModule_ProvideAchievementDaoFactory.provideAchievementDao(singletonCImpl.provideHabitDatabaseProvider.get());

          case 16: // com.app.habittracker.domain.engines.SmartQuestEngine 
          return (T) new SmartQuestEngine();

          case 17: // com.app.habittracker.workers.HabitStatusWorker_AssistedFactory 
          return (T) new HabitStatusWorker_AssistedFactory() {
            @Override
            public HabitStatusWorker create(Context context2, WorkerParameters workerParams2) {
              return new HabitStatusWorker(context2, workerParams2, singletonCImpl.habitRepositoryProvider.get(), singletonCImpl.gamificationEngineProvider.get());
            }
          };

          case 18: // com.app.habittracker.workers.SmartReminderWorker_AssistedFactory 
          return (T) new SmartReminderWorker_AssistedFactory() {
            @Override
            public SmartReminderWorker create(Context context3, WorkerParameters workerParams3) {
              return new SmartReminderWorker(context3, workerParams3, singletonCImpl.habitRepositoryProvider.get(), singletonCImpl.questRepositoryProvider.get(), singletonCImpl.userRepositoryProvider.get());
            }
          };

          case 19: // com.app.habittracker.workers.SnoozeWorker_AssistedFactory 
          return (T) new SnoozeWorker_AssistedFactory() {
            @Override
            public SnoozeWorker create(Context context4, WorkerParameters workerParams4) {
              return new SnoozeWorker(context4, workerParams4, singletonCImpl.habitRepositoryProvider.get());
            }
          };

          case 20: // com.app.habittracker.domain.repository.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.providePreferencesManagerProvider.get(), singletonCImpl.provideUserDaoProvider.get());

          case 21: // com.app.habittracker.domain.engines.TimerManager 
          return (T) new TimerManager(singletonCImpl.providePreferencesManagerProvider.get(), singletonCImpl.provideApplicationScopeProvider.get());

          case 22: // @com.app.habittracker.di.ApplicationScope kotlinx.coroutines.CoroutineScope 
          return (T) AppModule_ProvideApplicationScopeFactory.provideApplicationScope();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
