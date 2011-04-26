package com.christophdietze.jack.client.view;

import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandlerAdapter;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.presenter.DragAndDropPresenter;
import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.christophdietze.jack.client.resources.PieceImageBundle;
import com.christophdietze.jack.client.resources.PieceImageProvider;
import com.christophdietze.jack.shared.board.ChessUtils;
import com.christophdietze.jack.shared.board.Piece;
import com.christophdietze.jack.shared.board.Position;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DragAndDropView implements DragAndDropPresenter.View {

	private static MyCss CSS = MyClientBundle.CSS;

	static {
		StyleInjector.inject(MyClientBundle.INSTANCE.gwtDndOverrides().getText());
		CSS.ensureInjected();
	}

	private DragAndDropPresenter model;

	private BoardPanel boardPanel;

	private Map<Image, Integer> imageMap = new HashMap<Image, Integer>();
	/** java.util.BitSet is not available in GWT */
	private boolean[] draggablesBitSet = new boolean[64];

	private MyDragController dragController;

	@Inject
	public DragAndDropView(DragAndDropPresenter model, BoardPanel boardPanel) {
		this.model = model;
		this.boardPanel = boardPanel;
		dragController = new MyDragController();
		dragController.setBehaviorDragStartSensitivity(3);

		initImageMap();
		updateDraggables();
		// initSelectionMove();

		model.bindView(this);
		Log.debug(this.getClass().getName() + " initialized");
	}

	private void initImageMap() {
		for (int index = 0; index < 64; ++index) {
			Image image = boardPanel.getSquares()[index].getImage();
			imageMap.put(image, index);
		}
	}

	@Override
	public void update() {
		updateDraggables();
	}

	private void updateDraggables() {
		Position position = model.getGame().getPosition();
		for (int index = 0; index < 64; ++index) {
			int viewIndex = model.getGame().isWhiteAtBottom() ? index : 63 - index;
			if (position.getPiece(index).isPiece() && !draggablesBitSet[viewIndex]) {
				draggablesBitSet[viewIndex] = true;
				Image image = boardPanel.getSquares()[viewIndex].getImage();
				dragController.makeDraggable(image);
			}
			if (!position.getPiece(index).isPiece() && draggablesBitSet[viewIndex]) {
				draggablesBitSet[viewIndex] = false;
				Image image = boardPanel.getSquares()[viewIndex].getImage();
				dragController.makeNotDraggable(image);
			}
		}
	}

	private class MyDragController extends AbstractDragController {

		private Image draggingImage = new Image();

		public MyDragController() {
			super(RootPanel.get());
			draggingImage.setResource(PieceImageBundle.INSTANCE.empty());
			draggingImage.addStyleName(CSS.draggingImage());
			draggingImage.setVisible(false);
			RootLayoutPanel.get().add(draggingImage);
			this.addDragHandler(new DragHandlerAdapter() {
				@Override
				public void onDragStart(DragStartEvent event) {
					draggingImage.setVisible(true);
					Image image = (Image) event.getContext().draggable;
					int index = imageMap.get(image);
					if (!model.getGame().isWhiteAtBottom()) {
						index = 63 - index;
					}
					Piece piece = model.getGame().getPosition().getPiece(index);
					draggingImage.setResource(PieceImageProvider.getImageResource(piece));
				}
				@Override
				public void onDragEnd(DragEndEvent event) {
					draggingImage.setVisible(false);
					Image srcImage = (Image) event.getContext().draggable;
					int fromIndex = imageMap.get(srcImage);
					int toIndex = calcIndexOfMouse();
					if (toIndex < 0) {
						return;
					}
					if (!model.getGame().isWhiteAtBottom()) {
						fromIndex = 63 - fromIndex;
						toIndex = 63 - toIndex;
					}
					model.makeMove(fromIndex, toIndex);
				}

			});
		}

		@Override
		public void dragMove() {
			int desiredLeft = context.desiredDraggableX;
			int desiredTop = context.desiredDraggableY;
			DOMUtil.fastSetElementPosition(draggingImage.getElement(), desiredLeft, desiredTop);
		}

		private int calcIndexOfMouse() {
			int squareWidth = boardPanel.getSquares()[0].getOffsetWidth();
			int leftMost = boardPanel.getSquares()[0].getAbsoluteLeft();
			int file = (context.mouseX - leftMost) / squareWidth;
			if (context.mouseX < leftMost || file >= 8) {
				return -1;
			}
			int squareHeight = boardPanel.getSquares()[0].getOffsetHeight();
			int bottom = boardPanel.getSquares()[0].getAbsoluteTop() + squareHeight;
			int rank = context.mouseY > bottom ? 100 : (bottom - context.mouseY) / squareHeight;
			if (context.mouseY > bottom || rank >= 8) {
				return -1;
			}
			return ChessUtils.toIndex(file, rank);
		}
	}
}
