package com.app.habittracker.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.habittracker.data.local.entities.UserEntity;
import com.app.habittracker.data.local.entities.XPHistoryEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserEntity> __insertionAdapterOfUserEntity;

  private final EntityInsertionAdapter<XPHistoryEntity> __insertionAdapterOfXPHistoryEntity;

  private final EntityDeletionOrUpdateAdapter<UserEntity> __updateAdapterOfUserEntity;

  private final SharedSQLiteStatement __preparedStmtOfIncrementXP;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLevel;

  private final SharedSQLiteStatement __preparedStmtOfUpdateXPBoost;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSkin;

  private final SharedSQLiteStatement __preparedStmtOfUpdateProfileSettings;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldXPHistory;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`phoneNumber`,`pin`,`name`,`totalXp`,`currentLevel`,`currentStreak`,`highestStreak`,`difficultyPreference`,`reminderTime`,`lastHabitCompletionTime`,`activeXpBoostUntil`,`currentSparkySkin`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getPhoneNumber());
        statement.bindString(3, entity.getPin());
        statement.bindString(4, entity.getName());
        statement.bindLong(5, entity.getTotalXp());
        statement.bindLong(6, entity.getCurrentLevel());
        statement.bindLong(7, entity.getCurrentStreak());
        statement.bindLong(8, entity.getHighestStreak());
        statement.bindString(9, entity.getDifficultyPreference());
        statement.bindString(10, entity.getReminderTime());
        statement.bindLong(11, entity.getLastHabitCompletionTime());
        statement.bindLong(12, entity.getActiveXpBoostUntil());
        statement.bindString(13, entity.getCurrentSparkySkin());
      }
    };
    this.__insertionAdapterOfXPHistoryEntity = new EntityInsertionAdapter<XPHistoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `xp_history` (`id`,`userId`,`amount`,`reason`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final XPHistoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindLong(3, entity.getAmount());
        statement.bindString(4, entity.getReason());
        statement.bindLong(5, entity.getTimestamp());
      }
    };
    this.__updateAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`phoneNumber` = ?,`pin` = ?,`name` = ?,`totalXp` = ?,`currentLevel` = ?,`currentStreak` = ?,`highestStreak` = ?,`difficultyPreference` = ?,`reminderTime` = ?,`lastHabitCompletionTime` = ?,`activeXpBoostUntil` = ?,`currentSparkySkin` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getPhoneNumber());
        statement.bindString(3, entity.getPin());
        statement.bindString(4, entity.getName());
        statement.bindLong(5, entity.getTotalXp());
        statement.bindLong(6, entity.getCurrentLevel());
        statement.bindLong(7, entity.getCurrentStreak());
        statement.bindLong(8, entity.getHighestStreak());
        statement.bindString(9, entity.getDifficultyPreference());
        statement.bindString(10, entity.getReminderTime());
        statement.bindLong(11, entity.getLastHabitCompletionTime());
        statement.bindLong(12, entity.getActiveXpBoostUntil());
        statement.bindString(13, entity.getCurrentSparkySkin());
        statement.bindString(14, entity.getId());
      }
    };
    this.__preparedStmtOfIncrementXP = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET totalXp = totalXp + ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLevel = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET currentLevel = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateXPBoost = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET activeXpBoostUntil = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSkin = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET currentSparkySkin = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateProfileSettings = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET name = ?, difficultyPreference = ?, reminderTime = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldXPHistory = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM xp_history WHERE userId = ? AND id NOT IN (SELECT id FROM xp_history WHERE userId = ? ORDER BY timestamp DESC LIMIT 50)";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserEntity.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertXPHistory(final XPHistoryEntity history,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfXPHistoryEntity.insert(history);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserEntity.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addXPWithHistory(final String userId, final int amount, final String reason,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> UserDao.DefaultImpls.addXPWithHistory(UserDao_Impl.this, userId, amount, reason, __cont), $completion);
  }

  @Override
  public Object incrementXP(final String userId, final int amount,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementXP.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, amount);
        _argIndex = 2;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementXP.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLevel(final String userId, final int newLevel,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLevel.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, newLevel);
        _argIndex = 2;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLevel.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateXPBoost(final String userId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateXPBoost.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateXPBoost.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSkin(final String userId, final String skin,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSkin.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, skin);
        _argIndex = 2;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSkin.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProfileSettings(final String userId, final String name,
      final String difficulty, final String reminderTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateProfileSettings.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, name);
        _argIndex = 2;
        _stmt.bindString(_argIndex, difficulty);
        _argIndex = 3;
        _stmt.bindString(_argIndex, reminderTime);
        _argIndex = 4;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateProfileSettings.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldXPHistory(final String userId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldXPHistory.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldXPHistory.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserEntity> getUserById(final String userId) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPin = CursorUtil.getColumnIndexOrThrow(_cursor, "pin");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfCurrentLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "currentLevel");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfHighestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "highestStreak");
          final int _cursorIndexOfDifficultyPreference = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyPreference");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final int _cursorIndexOfLastHabitCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastHabitCompletionTime");
          final int _cursorIndexOfActiveXpBoostUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "activeXpBoostUntil");
          final int _cursorIndexOfCurrentSparkySkin = CursorUtil.getColumnIndexOrThrow(_cursor, "currentSparkySkin");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPin;
            _tmpPin = _cursor.getString(_cursorIndexOfPin);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final int _tmpCurrentLevel;
            _tmpCurrentLevel = _cursor.getInt(_cursorIndexOfCurrentLevel);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpHighestStreak;
            _tmpHighestStreak = _cursor.getInt(_cursorIndexOfHighestStreak);
            final String _tmpDifficultyPreference;
            _tmpDifficultyPreference = _cursor.getString(_cursorIndexOfDifficultyPreference);
            final String _tmpReminderTime;
            _tmpReminderTime = _cursor.getString(_cursorIndexOfReminderTime);
            final long _tmpLastHabitCompletionTime;
            _tmpLastHabitCompletionTime = _cursor.getLong(_cursorIndexOfLastHabitCompletionTime);
            final long _tmpActiveXpBoostUntil;
            _tmpActiveXpBoostUntil = _cursor.getLong(_cursorIndexOfActiveXpBoostUntil);
            final String _tmpCurrentSparkySkin;
            _tmpCurrentSparkySkin = _cursor.getString(_cursorIndexOfCurrentSparkySkin);
            _result = new UserEntity(_tmpId,_tmpPhoneNumber,_tmpPin,_tmpName,_tmpTotalXp,_tmpCurrentLevel,_tmpCurrentStreak,_tmpHighestStreak,_tmpDifficultyPreference,_tmpReminderTime,_tmpLastHabitCompletionTime,_tmpActiveXpBoostUntil,_tmpCurrentSparkySkin);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUserByPhoneAndPin(final String phone, final String pin,
      final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE phoneNumber = ? AND pin = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, phone);
    _argIndex = 2;
    _statement.bindString(_argIndex, pin);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPin = CursorUtil.getColumnIndexOrThrow(_cursor, "pin");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfCurrentLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "currentLevel");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfHighestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "highestStreak");
          final int _cursorIndexOfDifficultyPreference = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyPreference");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final int _cursorIndexOfLastHabitCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastHabitCompletionTime");
          final int _cursorIndexOfActiveXpBoostUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "activeXpBoostUntil");
          final int _cursorIndexOfCurrentSparkySkin = CursorUtil.getColumnIndexOrThrow(_cursor, "currentSparkySkin");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPin;
            _tmpPin = _cursor.getString(_cursorIndexOfPin);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final int _tmpCurrentLevel;
            _tmpCurrentLevel = _cursor.getInt(_cursorIndexOfCurrentLevel);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpHighestStreak;
            _tmpHighestStreak = _cursor.getInt(_cursorIndexOfHighestStreak);
            final String _tmpDifficultyPreference;
            _tmpDifficultyPreference = _cursor.getString(_cursorIndexOfDifficultyPreference);
            final String _tmpReminderTime;
            _tmpReminderTime = _cursor.getString(_cursorIndexOfReminderTime);
            final long _tmpLastHabitCompletionTime;
            _tmpLastHabitCompletionTime = _cursor.getLong(_cursorIndexOfLastHabitCompletionTime);
            final long _tmpActiveXpBoostUntil;
            _tmpActiveXpBoostUntil = _cursor.getLong(_cursorIndexOfActiveXpBoostUntil);
            final String _tmpCurrentSparkySkin;
            _tmpCurrentSparkySkin = _cursor.getString(_cursorIndexOfCurrentSparkySkin);
            _result = new UserEntity(_tmpId,_tmpPhoneNumber,_tmpPin,_tmpName,_tmpTotalXp,_tmpCurrentLevel,_tmpCurrentStreak,_tmpHighestStreak,_tmpDifficultyPreference,_tmpReminderTime,_tmpLastHabitCompletionTime,_tmpActiveXpBoostUntil,_tmpCurrentSparkySkin);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserByPhone(final String phone,
      final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE phoneNumber = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, phone);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPin = CursorUtil.getColumnIndexOrThrow(_cursor, "pin");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfCurrentLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "currentLevel");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfHighestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "highestStreak");
          final int _cursorIndexOfDifficultyPreference = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyPreference");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final int _cursorIndexOfLastHabitCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastHabitCompletionTime");
          final int _cursorIndexOfActiveXpBoostUntil = CursorUtil.getColumnIndexOrThrow(_cursor, "activeXpBoostUntil");
          final int _cursorIndexOfCurrentSparkySkin = CursorUtil.getColumnIndexOrThrow(_cursor, "currentSparkySkin");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPin;
            _tmpPin = _cursor.getString(_cursorIndexOfPin);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final int _tmpCurrentLevel;
            _tmpCurrentLevel = _cursor.getInt(_cursorIndexOfCurrentLevel);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpHighestStreak;
            _tmpHighestStreak = _cursor.getInt(_cursorIndexOfHighestStreak);
            final String _tmpDifficultyPreference;
            _tmpDifficultyPreference = _cursor.getString(_cursorIndexOfDifficultyPreference);
            final String _tmpReminderTime;
            _tmpReminderTime = _cursor.getString(_cursorIndexOfReminderTime);
            final long _tmpLastHabitCompletionTime;
            _tmpLastHabitCompletionTime = _cursor.getLong(_cursorIndexOfLastHabitCompletionTime);
            final long _tmpActiveXpBoostUntil;
            _tmpActiveXpBoostUntil = _cursor.getLong(_cursorIndexOfActiveXpBoostUntil);
            final String _tmpCurrentSparkySkin;
            _tmpCurrentSparkySkin = _cursor.getString(_cursorIndexOfCurrentSparkySkin);
            _result = new UserEntity(_tmpId,_tmpPhoneNumber,_tmpPin,_tmpName,_tmpTotalXp,_tmpCurrentLevel,_tmpCurrentStreak,_tmpHighestStreak,_tmpDifficultyPreference,_tmpReminderTime,_tmpLastHabitCompletionTime,_tmpActiveXpBoostUntil,_tmpCurrentSparkySkin);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
