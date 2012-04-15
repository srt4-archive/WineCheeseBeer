require 'test_helper'

class ItemIngredientsControllerTest < ActionController::TestCase
  setup do
    @item_ingredient = item_ingredients(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:item_ingredients)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create item_ingredient" do
    assert_difference('ItemIngredient.count') do
      post :create, item_ingredient: @item_ingredient.attributes
    end

    assert_redirected_to item_ingredient_path(assigns(:item_ingredient))
  end

  test "should show item_ingredient" do
    get :show, id: @item_ingredient
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @item_ingredient
    assert_response :success
  end

  test "should update item_ingredient" do
    put :update, id: @item_ingredient, item_ingredient: @item_ingredient.attributes
    assert_redirected_to item_ingredient_path(assigns(:item_ingredient))
  end

  test "should destroy item_ingredient" do
    assert_difference('ItemIngredient.count', -1) do
      delete :destroy, id: @item_ingredient
    end

    assert_redirected_to item_ingredients_path
  end
end
