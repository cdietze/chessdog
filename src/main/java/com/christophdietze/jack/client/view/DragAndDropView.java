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
import com.christophdietze.jack.client.resources.PieceImageBundle;
import com.christophdietze.jack.client.resources.PieceImageProvider;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.Position;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DragAndDropView implements DragAndDropPresenter.View {
	static {
		StyleInjector.inject(MyClientBundle.INSTANCE.gwtDndOverrides().getText());
	}

	private DragAndDropPresenter model;

	private BoardView boardView;

	private Map<Image, Integer> imageMap = new HashMap<Image, Integer>();
	private boolean[] draggablesBitSet = new boolean[64];

	private MyDragController dragController;

	@Inject
	public DragAndDropView(DragAndDropPresenter model, BoardView boardView) {
		this.model = model;
		this.boardView = boardView;

		init();
		model.setView(this);
	}

	private void init() {
		dragController = new MyDragController();

		initImageMap();
		updateDraggables();

		Log.debug("DragNDrop initialized");
	}

	private void initImageMap() {
		for (int index = 0; index < 64; ++index) {
			Image image = boardView.getSquareImages()[index];
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
			if (position.getPiece(index).isPiece() && !draggablesBitSet[index]) {
				draggablesBitSet[index] = true;
				Image image = boardView.getSquareImages()[index];
				dragController.makeDraggable(image);
			}
			if (!position.getPiece(index).isPiece() && draggablesBitSet[index]) {
				draggablesBitSet[index] = false;
				Image image = boardView.getSquareImages()[index];
				dragController.makeNotDraggable(image);
			}
		}
	}

	private class MyDragController extends AbstractDragController {

		private Image draggingImage = new Image();

		public MyDragController() {
			super(RootPanel.get());
			draggingImage.setResource(PieceImageBundle.INSTANCE.bbishop());
			draggingImage.getElement().getStyle().setPosition(com.google.gwt.dom.client.Style.Position.ABSOLUTE);
			draggingImage.setVisible(false);
			RootPanel.get().add(draggingImage);
			this.addDragHandler(new DragHandlerAdapter() {
				@Override
				public void onDragStart(DragStartEvent event) {
					draggingImage.setVisible(true);
					Image image = (Image) event.getContext().draggable;
					int index = imageMap.get(image);
					Piece piece = model.getGame().getPosition().getPiece(index);
					draggingImage.setResource(PieceImageProvider.getImageResource(piece));
				}
				@Override
				public void onDragEnd(DragEndEvent event) {
					draggingImage.setVisible(false);
					Image srcImage = (Image) event.getContext().draggable;
					int fromIndex = imageMap.get(srcImage);
					int toIndex = calcIndexOfMouse();
					model.movePiece(fromIndex, toIndex);
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
			int squareWidth = boardView.getSquareImages()[0].getWidth();
			int leftMost = boardView.getSquareImages()[0].getAbsoluteLeft();
			int file = (context.mouseX - leftMost) / squareWidth;
			if (context.mouseX < leftMost || file >= 8) {
				return -1;
			}
			int squareHeight = boardView.getSquareImages()[0].getHeight();
			int bottom = boardView.getSquareImages()[0].getAbsoluteTop() + squareHeight;
			int rank = context.mouseY > bottom ? 100 : (bottom - context.mouseY) / squareHeight;
			if (context.mouseY > bottom || rank >= 8) {
				return -1;
			}
			return ChessUtils.toIndex(file, rank);
		}
	}
}
