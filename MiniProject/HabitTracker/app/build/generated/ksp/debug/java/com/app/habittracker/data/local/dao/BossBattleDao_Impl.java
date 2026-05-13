package com.app.habittracker.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.habittracker.data.local.entities.BossBattleEntity;
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
public final class BossBattleDao_Impl implements BossBattleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BossBattleEntity> __insertionAdapterOfBossBattleEntity;

  private final EntityDeletionOrUpdateAdapter<BossBattleEntity> __updateAdapterOfBossBattleEntity;

  public BossBattleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBossBattleEntity = new EntityInsertionAdapter<BossBattleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `boss_battles` (`id`,`userId`,`name`,`description`,`task1Name`,`task1Desc`,`task1Completed`,`task1StartTime`,`task1EndTime`,`task1Duration`,`task2Name`,`task2Desc`,`task2Completed`,`task2StartTime`,`task2EndTime`,`task2Duration`,`task3Name`,`task3Desc`,`task3Completed`,`task3StartTime`,`task3EndTime`,`task3Duration`,`currentHp`,`maxHp`,`startDate`,`endDate`,`isDefeated`,`isEnraged`,`rewardXp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BossBattleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getTask1Name());
        statement.bindString(6, entity.getTask1Desc());
        final int _tmp = entity.getTask1Completed() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindString(8, entity.getTask1StartTime());
        statement.bindString(9, entity.getTask1EndTime());
        statement.bindLong(10, entity.getTask1Duration());
        statement.bindString(11, entity.getTask2Name());
        statement.bindString(12, entity.getTask2Desc());
        final int _tmp_1 = entity.getTask2Completed() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        statement.bindString(14, entity.getTask2StartTime());
        statement.bindString(15, entity.getTask2EndTime());
        statement.bindLong(16, entity.getTask2Duration());
        statement.bindString(17, entity.getTask3Name());
        statement.bindString(18, entity.getTask3Desc());
        final int _tmp_2 = entity.getTask3Completed() ? 1 : 0;
        statement.bindLong(19, _tmp_2);
        statement.bindString(20, entity.getTask3StartTime());
        statement.bindString(21, entity.getTask3EndTime());
        statement.bindLong(22, entity.getTask3Duration());
        statement.bindLong(23, entity.getCurrentHp());
        statement.bindLong(24, entity.getMaxHp());
        statement.bindLong(25, entity.getStartDate());
        statement.bindLong(26, entity.getEndDate());
        final int _tmp_3 = entity.isDefeated() ? 1 : 0;
        statement.bindLong(27, _tmp_3);
        final int _tmp_4 = entity.isEnraged() ? 1 : 0;
        statement.bindLong(28, _tmp_4);
        statement.bindLong(29, entity.getRewardXp());
      }
    };
    this.__updateAdapterOfBossBattleEntity = new EntityDeletionOrUpdateAdapter<BossBattleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `boss_battles` SET `id` = ?,`userId` = ?,`name` = ?,`description` = ?,`task1Name` = ?,`task1Desc` = ?,`task1Completed` = ?,`task1StartTime` = ?,`task1EndTime` = ?,`task1Duration` = ?,`task2Name` = ?,`task2Desc` = ?,`task2Completed` = ?,`task2StartTime` = ?,`task2EndTime` = ?,`task2Duration` = ?,`task3Name` = ?,`task3Desc` = ?,`task3Completed` = ?,`task3StartTime` = ?,`task3EndTime` = ?,`task3Duration` = ?,`currentHp` = ?,`maxHp` = ?,`startDate` = ?,`endDate` = ?,`isDefeated` = ?,`isEnraged` = ?,`rewardXp` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BossBattleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getTask1Name());
        statement.bindString(6, entity.getTask1Desc());
        final int _tmp = entity.getTask1Completed() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindString(8, entity.getTask1StartTime());
        statement.bindString(9, entity.getTask1EndTime());
        statement.bindLong(10, entity.getTask1Duration());
        statement.bindString(11, entity.getTask2Name());
        statement.bindString(12, entity.getTask2Desc());
        final int _tmp_1 = entity.getTask2Completed() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        statement.bindString(14, entity.getTask2StartTime());
        statement.bindString(15, entity.getTask2EndTime());
        statement.bindLong(16, entity.getTask2Duration());
        statement.bindString(17, entity.getTask3Name());
        statement.bindString(18, entity.getTask3Desc());
        final int _tmp_2 = entity.getTask3Completed() ? 1 : 0;
        statement.bindLong(19, _tmp_2);
        statement.bindString(20, entity.getTask3StartTime());
        statement.bindString(21, entity.getTask3EndTime());
        statement.bindLong(22, entity.getTask3Duration());
        statement.bindLong(23, entity.getCurrentHp());
        statement.bindLong(24, entity.getMaxHp());
        statement.bindLong(25, entity.getStartDate());
        statement.bindLong(26, entity.getEndDate());
        final int _tmp_3 = entity.isDefeated() ? 1 : 0;
        statement.bindLong(27, _tmp_3);
        final int _tmp_4 = entity.isEnraged() ? 1 : 0;
        statement.bindLong(28, _tmp_4);
        statement.bindLong(29, entity.getRewardXp());
        statement.bindLong(30, entity.getId());
      }
    };
  }

  @Override
  public Object insertBossBattle(final BossBattleEntity bossBattle,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBossBattleEntity.insert(bossBattle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBossBattle(final BossBattleEntity bossBattle,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBossBattleEntity.handle(bossBattle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<BossBattleEntity> getCurrentBossBattle(final String userId) {
    final String _sql = "SELECT * FROM boss_battles WHERE userId = ? ORDER BY id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"boss_battles"}, new Callable<BossBattleEntity>() {
      @Override
      @Nullable
      public BossBattleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTask1Name = CursorUtil.getColumnIndexOrThrow(_cursor, "task1Name");
          final int _cursorIndexOfTask1Desc = CursorUtil.getColumnIndexOrThrow(_cursor, "task1Desc");
          final int _cursorIndexOfTask1Completed = CursorUtil.getColumnIndexOrThrow(_cursor, "task1Completed");
          final int _cursorIndexOfTask1StartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "task1StartTime");
          final int _cursorIndexOfTask1EndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "task1EndTime");
          final int _cursorIndexOfTask1Duration = CursorUtil.getColumnIndexOrThrow(_cursor, "task1Duration");
          final int _cursorIndexOfTask2Name = CursorUtil.getColumnIndexOrThrow(_cursor, "task2Name");
          final int _cursorIndexOfTask2Desc = CursorUtil.getColumnIndexOrThrow(_cursor, "task2Desc");
          final int _cursorIndexOfTask2Completed = CursorUtil.getColumnIndexOrThrow(_cursor, "task2Completed");
          final int _cursorIndexOfTask2StartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "task2StartTime");
          final int _cursorIndexOfTask2EndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "task2EndTime");
          final int _cursorIndexOfTask2Duration = CursorUtil.getColumnIndexOrThrow(_cursor, "task2Duration");
          final int _cursorIndexOfTask3Name = CursorUtil.getColumnIndexOrThrow(_cursor, "task3Name");
          final int _cursorIndexOfTask3Desc = CursorUtil.getColumnIndexOrThrow(_cursor, "task3Desc");
          final int _cursorIndexOfTask3Completed = CursorUtil.getColumnIndexOrThrow(_cursor, "task3Completed");
          final int _cursorIndexOfTask3StartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "task3StartTime");
          final int _cursorIndexOfTask3EndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "task3EndTime");
          final int _cursorIndexOfTask3Duration = CursorUtil.getColumnIndexOrThrow(_cursor, "task3Duration");
          final int _cursorIndexOfCurrentHp = CursorUtil.getColumnIndexOrThrow(_cursor, "currentHp");
          final int _cursorIndexOfMaxHp = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHp");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfIsDefeated = CursorUtil.getColumnIndexOrThrow(_cursor, "isDefeated");
          final int _cursorIndexOfIsEnraged = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnraged");
          final int _cursorIndexOfRewardXp = CursorUtil.getColumnIndexOrThrow(_cursor, "rewardXp");
          final BossBattleEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpTask1Name;
            _tmpTask1Name = _cursor.getString(_cursorIndexOfTask1Name);
            final String _tmpTask1Desc;
            _tmpTask1Desc = _cursor.getString(_cursorIndexOfTask1Desc);
            final boolean _tmpTask1Completed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfTask1Completed);
            _tmpTask1Completed = _tmp != 0;
            final String _tmpTask1StartTime;
            _tmpTask1StartTime = _cursor.getString(_cursorIndexOfTask1StartTime);
            final String _tmpTask1EndTime;
            _tmpTask1EndTime = _cursor.getString(_cursorIndexOfTask1EndTime);
            final int _tmpTask1Duration;
            _tmpTask1Duration = _cursor.getInt(_cursorIndexOfTask1Duration);
            final String _tmpTask2Name;
            _tmpTask2Name = _cursor.getString(_cursorIndexOfTask2Name);
            final String _tmpTask2Desc;
            _tmpTask2Desc = _cursor.getString(_cursorIndexOfTask2Desc);
            final boolean _tmpTask2Completed;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTask2Completed);
            _tmpTask2Completed = _tmp_1 != 0;
            final String _tmpTask2StartTime;
            _tmpTask2StartTime = _cursor.getString(_cursorIndexOfTask2StartTime);
            final String _tmpTask2EndTime;
            _tmpTask2EndTime = _cursor.getString(_cursorIndexOfTask2EndTime);
            final int _tmpTask2Duration;
            _tmpTask2Duration = _cursor.getInt(_cursorIndexOfTask2Duration);
            final String _tmpTask3Name;
            _tmpTask3Name = _cursor.getString(_cursorIndexOfTask3Name);
            final String _tmpTask3Desc;
            _tmpTask3Desc = _cursor.getString(_cursorIndexOfTask3Desc);
            final boolean _tmpTask3Completed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfTask3Completed);
            _tmpTask3Completed = _tmp_2 != 0;
            final String _tmpTask3StartTime;
            _tmpTask3StartTime = _cursor.getString(_cursorIndexOfTask3StartTime);
            final String _tmpTask3EndTime;
            _tmpTask3EndTime = _cursor.getString(_cursorIndexOfTask3EndTime);
            final int _tmpTask3Duration;
            _tmpTask3Duration = _cursor.getInt(_cursorIndexOfTask3Duration);
            final int _tmpCurrentHp;
            _tmpCurrentHp = _cursor.getInt(_cursorIndexOfCurrentHp);
            final int _tmpMaxHp;
            _tmpMaxHp = _cursor.getInt(_cursorIndexOfMaxHp);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final boolean _tmpIsDefeated;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDefeated);
            _tmpIsDefeated = _tmp_3 != 0;
            final boolean _tmpIsEnraged;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsEnraged);
            _tmpIsEnraged = _tmp_4 != 0;
            final int _tmpRewardXp;
            _tmpRewardXp = _cursor.getInt(_cursorIndexOfRewardXp);
            _result = new BossBattleEntity(_tmpId,_tmpUserId,_tmpName,_tmpDescription,_tmpTask1Name,_tmpTask1Desc,_tmpTask1Completed,_tmpTask1StartTime,_tmpTask1EndTime,_tmpTask1Duration,_tmpTask2Name,_tmpTask2Desc,_tmpTask2Completed,_tmpTask2StartTime,_tmpTask2EndTime,_tmpTask2Duration,_tmpTask3Name,_tmpTask3Desc,_tmpTask3Completed,_tmpTask3StartTime,_tmpTask3EndTime,_tmpTask3Duration,_tmpCurrentHp,_tmpMaxHp,_tmpStartDate,_tmpEndDate,_tmpIsDefeated,_tmpIsEnraged,_tmpRewardXp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
