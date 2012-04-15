class ItemIngredientsController < ApplicationController
  # GET /item_ingredients
  # GET /item_ingredients.json
  def index
    @item_ingredients = ItemIngredient.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @item_ingredients }
    end
  end

  # GET /item_ingredients/1
  # GET /item_ingredients/1.json
  def show
    @item_ingredient = ItemIngredient.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @item_ingredient }
    end
  end

  # GET /item_ingredients/new
  # GET /item_ingredients/new.json
  def new
    @item_ingredient = ItemIngredient.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @item_ingredient }
    end
  end

  # GET /item_ingredients/1/edit
  def edit
    @item_ingredient = ItemIngredient.find(params[:id])
  end

  # POST /item_ingredients
  # POST /item_ingredients.json
  def create
    @item_ingredient = ItemIngredient.new(params[:item_ingredient])

    respond_to do |format|
      if @item_ingredient.save
        format.html { redirect_to @item_ingredient, notice: 'Item ingredient was successfully created.' }
        format.json { render json: @item_ingredient, status: :created, location: @item_ingredient }
      else
        format.html { render action: "new" }
        format.json { render json: @item_ingredient.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /item_ingredients/1
  # PUT /item_ingredients/1.json
  def update
    @item_ingredient = ItemIngredient.find(params[:id])

    respond_to do |format|
      if @item_ingredient.update_attributes(params[:item_ingredient])
        format.html { redirect_to @item_ingredient, notice: 'Item ingredient was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @item_ingredient.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /item_ingredients/1
  # DELETE /item_ingredients/1.json
  def destroy
    @item_ingredient = ItemIngredient.find(params[:id])
    @item_ingredient.destroy

    respond_to do |format|
      format.html { redirect_to item_ingredients_url }
      format.json { head :no_content }
    end
  end
end
