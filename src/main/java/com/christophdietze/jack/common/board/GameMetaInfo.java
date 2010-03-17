package com.christophdietze.jack.common.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.christophdietze.jack.common.util.SimpleToStringBuilder;

public class GameMetaInfo {

	private List<GameMetaInfoListener> listeners = new ArrayList<GameMetaInfoListener>();

	private String whitePlayerName;
	private String blackPlayerName;

	private Map<String, String> additionalTags = new HashMap<String, String>();

	public String getWhitePlayerName() {
		return whitePlayerName;
	}

	public String getBlackPlayerName() {
		return blackPlayerName;
	}

	public void setWhitePlayerName(String whitePlayerName) {
		this.whitePlayerName = whitePlayerName;
		fireMetaInfoChanged();
	}

	public void setBlackPlayerName(String blackPlayerName) {
		this.blackPlayerName = blackPlayerName;
		fireMetaInfoChanged();
	}

	public void clear() {
		whitePlayerName = null;
		blackPlayerName = null;
		additionalTags.clear();
		fireMetaInfoChanged();
	}

	public void addTag(String name, String value) {
		additionalTags.put(name, value);
	}

	public String getTag(String tagName) {
		return additionalTags.get(tagName);
	}

	public Map<String, String> getTags() {
		return additionalTags;
	}

	public void addListener(GameMetaInfoListener listener) {
		listeners.add(listener);
	}

	private void fireMetaInfoChanged() {
		for (GameMetaInfoListener listener : listeners) {
			listener.onMetaInfoChanged();
		}
	}

	public static class TagPair {
		private String name;
		private String value;

		public TagPair(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public TagPair(Map.Entry<String, String> mapEntry) {
			this(mapEntry.getKey(), mapEntry.getValue());
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return SimpleToStringBuilder.forObject(this).append("name", name)
					.append("value", value).toString();
		}
	}
}
