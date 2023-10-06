package com.akshaw.drinkreminder.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut


object Animations {
    
    object Default {
        
        private const val animTweenDuration = 20
        
        fun enter() = fadeIn(tween(animTweenDuration))
        fun exit() = fadeOut(tween(animTweenDuration))
        
    }
    
    object AppHorizontalSlide {
        
        private const val animTweenDuration = 150
        
        fun enter(scope: AnimatedContentTransitionScope<*>): EnterTransition {
            return scope.slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animTweenDuration),
                initialOffset = { it / 10 }
            ) + fadeIn(
                animationSpec = tween(animTweenDuration)
            )
        }
        
        fun exit(scope: AnimatedContentTransitionScope<*>): ExitTransition {
            return scope.slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animTweenDuration),
                targetOffset = { it / 10 }
            ) + fadeOut(
                animationSpec = tween(animTweenDuration)
            )
        }
        
        fun popEnter(scope: AnimatedContentTransitionScope<*>): EnterTransition {
            return scope.slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animTweenDuration),
                initialOffset = { it / 10 }
            ) + fadeIn(
                animationSpec = tween(animTweenDuration)
            )
        }
        
        fun popExit(scope: AnimatedContentTransitionScope<*>): ExitTransition {
            return scope.slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animTweenDuration),
                targetOffset = { it / 10 }
            ) + fadeOut(
                animationSpec = tween(animTweenDuration)
            )
        }
    }
    
    
    object AppVerticalSlide {
        
        private const val animTweenDuration = 150
        
        fun enter(scope: AnimatedContentTransitionScope<*>): EnterTransition {
            return scope.slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(animTweenDuration),
                initialOffset = { it / 10 }
            ) + fadeIn(
                animationSpec = tween(animTweenDuration)
            )
        }
        
        fun exit(scope: AnimatedContentTransitionScope<*>): ExitTransition {
            return scope.slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(animTweenDuration),
                targetOffset = { it / 10 }
            ) + fadeOut(
                animationSpec = tween(animTweenDuration)
            )
        }
        
        fun popEnter(scope: AnimatedContentTransitionScope<*>): EnterTransition {
            return scope.slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(animTweenDuration),
                initialOffset = { it / 10 }
            ) + fadeIn(
                animationSpec = tween(animTweenDuration)
            )
        }
        
        fun popExit(scope: AnimatedContentTransitionScope<*>): ExitTransition {
            return scope.slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(animTweenDuration),
                targetOffset = { it / 10 }
            ) + fadeOut(
                animationSpec = tween(animTweenDuration)
            )
        }
    }
    
    
}
